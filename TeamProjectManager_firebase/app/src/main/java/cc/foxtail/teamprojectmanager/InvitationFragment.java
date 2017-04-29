package cc.foxtail.teamprojectmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;

public class InvitationFragment extends Fragment {
    private InvitationAdapter mAdapter;
    private List<Invitation> invitationList;
    private DatabaseReference mDatabase;
    private String userId;
    private User userObj;
    private String googleUid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String[] userIdEmail = new String[0];

        if (firebaseUser != null) {
            userIdEmail = firebaseUser.getEmail().split("@");
        } //수정했음
        userId = userIdEmail[0];
        googleUid = firebaseUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.invitation_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        userObj = getArguments().getParcelable("InvitationFragment");

        invitationList = new ArrayList<>();
        readInvitation();

        mAdapter = new InvitationAdapter(invitationList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public class InvitationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSenderNameTextView;
        private TextView mTeamProjectTitleTextView;
        private Invitation mInvitation;

        public InvitationHolder(View itemView) {
            super(itemView);

            mSenderNameTextView = (TextView) itemView.findViewById(R.id.invitation_sender_name);
            mTeamProjectTitleTextView = (TextView) itemView.findViewById(R.id.invitation_team_project_title);
            itemView.setOnClickListener(this);

        }

        public void bindInvitation(Invitation invitation) {
            this.mInvitation = invitation;
            mSenderNameTextView.setText(mInvitation.getSenderName());
            mTeamProjectTitleTextView.setText(mInvitation.getTeamProjectTitle());
        }

        @Override
        public void onClick(View v) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_invitation, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            TextView senderNameTextView = (TextView) dialogView.findViewById(R.id.invitation_dialog_sender_name_text_view);
            TextView teamProjectTitleTextView = (TextView) dialogView.findViewById(R.id.invitation_dialog_team_project_title_text_view);

            senderNameTextView.setText(mInvitation.getSenderName());
            teamProjectTitleTextView.setText(mInvitation.getTeamProjectTitle());

            builder.setTitle("팀 프로젝트 초대");
            builder.setView(dialogView);

            builder.setPositiveButton("초대 수락", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userObj.getTeamProjectList().add(mInvitation.getTeamProjectTitle());
//                    mDatabase.child("TeamProject").child(mInvitation.getTeamProjectTitle()).child("userList").push().

                    mDatabase.child("User").child(googleUid).setValue(userObj);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
    }

    public class InvitationAdapter extends RecyclerView.Adapter<InvitationHolder> {
        private List<Invitation> invitationList;

        public InvitationAdapter(List<Invitation> invitationList) {
            this.invitationList = invitationList;
        }

        @Override
        public InvitationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_invitation, parent, false);


            return new InvitationHolder(view);
        }

        @Override
        public void onBindViewHolder(final InvitationHolder holder, int position) {
            Invitation invitation = invitationList.get(position);
            holder.bindInvitation(invitation);
        }

        @Override
        public int getItemCount() {
            return invitationList.size();
        }
    }

    private void readInvitation() {
        mDatabase.child("Invitation").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Invitation invitation = dataSnapshot.getValue(Invitation.class);
                invitationList.add(invitation);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static InvitationFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable("InvitationFragment", user);

        InvitationFragment fragment = new InvitationFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
