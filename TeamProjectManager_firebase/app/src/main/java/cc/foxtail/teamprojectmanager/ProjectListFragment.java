package cc.foxtail.teamprojectmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mUserListRecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private ProjectAdapter projectAdapter;
    private UserListAdapter userListAdapter;
    private List<TeamProject> teamProjectList;
    private User userObj;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.project_recycler_view);
        userObj = getArguments().getParcelable("ProjectListFragment");
        readUserData();

        teamProjectList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.floating_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_team_project_create, null);
                AlertDialog.Builder buider = new AlertDialog.Builder(getActivity());
                buider.setTitle("팀 프로젝트 생성");
                buider.setView(dialogView);

                mUserListRecyclerView = (RecyclerView) dialogView.findViewById(R.id.user_search_recycler_view);
                userList = new ArrayList<>();
                userListAdapter = new UserListAdapter(userList);
                mUserListRecyclerView.setAdapter(userListAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mUserListRecyclerView.setLayoutManager(layoutManager);

                final EditText userEdit = (EditText) dialogView.findViewById(R.id.user_search_edit_text);
                userEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = userEdit.getText().toString()
                                .toLowerCase(Locale.getDefault());
                        Log.d("Test", text);
                        userSearch(text);
                    }
                });


                buider.setPositiveButton("생성 완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText ProjectNameEditText = (EditText) dialogView.findViewById(R.id.project_name_edit_text);
                        String projectName = ProjectNameEditText.getText().toString();
                        TeamProject teamProject = new TeamProject();
                        teamProject.setTitle(projectName);

                        teamProject.getUserList().add(userObj.getUserName());

                        mDatabase.child("TeamProject").child(projectName).setValue(teamProject);
                        userObj.getTeamProjectList().add(teamProject.getTitle());

                        mDatabase.child("User").child(firebaseUser.getUid()).setValue(userObj);
                        for (User user : userList) {
                            Invitation invitation = new Invitation(userObj.getUserName(), projectName);
                            String[] receiver = user.getUserEmail().split("@");
                            mDatabase.child("Invitation").child(receiver[0]).setValue(invitation);
                        }

                        teamProjectList.add(teamProject);
                        UpdateUI();
                    }
                });
                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = buider.create();

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        });

        UpdateUI();

        return view;
    }

    private void readUserData() {
        for (int i = 0; i < userObj.getTeamProjectList().size(); i++) {
            loadTeamProject(userObj.getTeamProjectList().get(i));
        }
    }

    private void loadTeamProject(String title) {

        mDatabase.child("TeamProject").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TeamProject team = dataSnapshot.getValue(TeamProject.class);

                for(String str : team.getUserList())
                    System.out.println(str);

                teamProjectList.add(team);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void UpdateUI() {
        projectAdapter = new ProjectAdapter(teamProjectList);
        mRecyclerView.setAdapter(projectAdapter);
    }


    private class ProjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleTextView;
        private TeamProject mTeamProject;


        public ProjectHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.card_view_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(mTeamProject.getTitle());

            Fragment fragment = ProjectFragment.newInstance(mTeamProject);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }

        public void bindTeamProject(TeamProject teamProject) {
            mTeamProject = teamProject;
            mTitleTextView.setText(mTeamProject.getTitle());
        }
    }

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {
        private List<TeamProject> mTeamProjectList;

        public ProjectAdapter(List<TeamProject> teamProjects) {
            mTeamProjectList = teamProjects;
        }

        @Override
        public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_project, parent, false);

            return new ProjectHolder(view);
        }

        @Override
        public void onBindViewHolder(ProjectHolder holder, int position) {
            TeamProject teamProject = mTeamProjectList.get(position);
            holder.bindTeamProject(teamProject);
        }

        @Override
        public int getItemCount() {
            return mTeamProjectList.size();
        }
    }

    public class UserListHolder extends RecyclerView.ViewHolder {
        private TextView mUserNameTextView;
        private User mUser;

        public UserListHolder(View itemView) {
            super(itemView);

            mUserNameTextView = (TextView) itemView.findViewById(R.id.user_name_text_view);

        }

        public void bindUser(User user) {
            mUser = user;
            mUserNameTextView.setText(mUser.getUserName());
        }

    }

    public class UserListAdapter extends RecyclerView.Adapter<UserListHolder> {
        private List<User> userList;

        public UserListAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public UserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_user, parent, false);


            return new UserListHolder(view);
        }

        @Override
        public void onBindViewHolder(final UserListHolder holder, int position) {
            User user = userList.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }


    }

    private void userSearch(String text) {
        final Query query = mDatabase.child("User").orderByChild("userName").equalTo(text);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildren().iterator().hasNext()) {
                    System.out.println("확인" + dataSnapshot.getChildren().iterator().next().toString());
                    User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    userList.add(user);

                    userListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Project Manager");
    }

    public static Fragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable("ProjectListFragment", user);

        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
