package cc.foxtail.teamprojectmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ProjectListFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.project_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.floating_add_button);

        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //oast.makeText(getActivity(),"floating",Toast.LENGTH_SHORT).show();

                LayoutInflater inflater= getLayoutInflater(savedInstanceState);
                final View dialogView= inflater.inflate(R.layout.dialog_team_project_create, null);
                AlertDialog.Builder buider= new AlertDialog.Builder(getActivity());
                buider.setTitle("팀 프로젝트 생성");
                buider.setView(dialogView);
                buider.setPositiveButton("생성 완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText ProjectName= (EditText)dialogView.findViewById(R.id.project_name_edit_text);
                        String name= ProjectName.getText().toString();

                        TeamProject teamProject = new TeamProject();
                        teamProject.setTitle(name);

                        TeamProjectLab.get().getTeamProjectList().add(teamProject);
                        UpdateUI();
                    }
                });
                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=buider.create();

                dialog.setCanceledOnTouchOutside(false); //dialog 없어지지 않도록 설정
                dialog.show();

            }
        });

        UpdateUI();

        return view;
    }

    private void UpdateUI() {
        TeamProjectLab teamProjectLab = TeamProjectLab.get();
        List<TeamProject> teamProjectList = teamProjectLab.getTeamProjectList();

        ProjectAdapter adapter = new ProjectAdapter(teamProjectList);
        mRecyclerView.setAdapter(adapter);
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

            Fragment fragment = ProjectFragment.newInstance(mTeamProject.getId());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
        }

        public void bindTeamProject(TeamProject teamProject){
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

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Project Manager");
    }
}
