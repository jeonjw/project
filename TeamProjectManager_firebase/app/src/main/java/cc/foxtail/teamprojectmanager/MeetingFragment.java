package cc.foxtail.teamprojectmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MeetingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TeamProject mTeamProject;
    private MeetingAdapter adapter;
    private MeetingTimeAdapter meetingTimeAdapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);

        final ScheduleFragment scheduleFragment = (ScheduleFragment) getParentFragment();
        scheduleFragment.clearSelection();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTeamProject = getArguments().getParcelable("MeetingFragment");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.meeting_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.floating_meeting_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleFragment.setSelectMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

                Fragment fragment = new AddMeetingFragment();
                fragment.setTargetFragment(MeetingFragment.this, -1);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.meeting_root_frame, fragment, "AddMeetingFragment").addToBackStack(null).commit();

            }
        });

        updateUI();

        return view;
    }


    private void updateUI() {
        adapter = new MeetingAdapter(mTeamProject.getMeetingList());
        mRecyclerView.setAdapter(adapter);
    }

    public void addMeeting(Meeting meeting) {
        mTeamProject.getMeetingList().add(meeting);
        adapter.notifyDataSetChanged();

        Query query = mDatabase.child("TeamProject").orderByChild("title").equalTo(mTeamProject.getTitle());
        query.getRef().child(mTeamProject.getTitle()).setValue(mTeamProject);
    }


    private class MeetingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private Meeting mMeeting;


        public MeetingHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.meeting_title);
            itemView.setOnClickListener(this);
        }


        public void bindMeeting(Meeting meeting) {
            mMeeting = meeting;
            mTitleTextView.setText(dateToString());
        }

        private String dateToString() {
            String startDate = mMeeting.getDateList().get(0);
            StringTokenizer startDateTokenizer = new StringTokenizer(startDate);
            String startYear = startDateTokenizer.nextToken(".");
            String startMonth = startDateTokenizer.nextToken(".");
            String startDay = startDateTokenizer.nextToken(".");

            startDate = startYear + "년 " + startMonth + "월 " + startDay + "일";

            for (int i = 1; i < mMeeting.getDateList().size(); i++) {
                String endDate = mMeeting.getDateList().get(i);
                StringTokenizer endDateTokenizer = new StringTokenizer(endDate);
                endDateTokenizer.nextToken(".");
                String endMonth = endDateTokenizer.nextToken(".");
                String endDay = endDateTokenizer.nextToken(".");

                startDate += ", " + endMonth + "월 " + endDay + "일";
            }

            return startDate;
        }

        @Override
        public void onClick(View v) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_meeting_time_select, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("모임시간 투표");
            builder.setView(dialogView);

            if (mMeeting.getVoteUserNum() == 3) {
                MeetingTime meetingTime = mMeeting.getElectedMeetingTime();
                TextView electionTextView = (TextView) dialogView.findViewById(R.id.election_text_view);
                electionTextView.setText("최다 투표 시간");

                TextView electedTimeTextView = (TextView) dialogView.findViewById(R.id.elected_time_text_view);
                electedTimeTextView.setText(meetingTime.getStartHour() + ":00" + " ~ " + meetingTime.getEndHour() + ":00");

            } else {

                TextView dateTextView = (TextView) dialogView.findViewById(R.id.meeting_select_date);

                dateTextView.setText(dateToString());

                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.time_select_recycler_view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                meetingTimeAdapter = new MeetingTimeAdapter(mMeeting.getMeetingTimeList());
                recyclerView.setAdapter(meetingTimeAdapter);

                builder.setPositiveButton("투표 완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<MeetingTime> checkedList = new ArrayList<>();

                        for (int i = 0; i < mMeeting.getMeetingTimeList().size(); i++) {
                            MeetingTime meetingTime = mMeeting.getMeetingTimeList().get(i);
                            if (meetingTime.isChecked()) {
                                meetingTime.setChecked(false);
                                checkedList.add(meetingTime);
                            }
                        }

                        mMeeting.selectMeetingTime(checkedList);
                    }
                });
            }
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

    private class MeetingAdapter extends RecyclerView.Adapter<MeetingHolder> {
        private List<Meeting> mMeetingList;

        public MeetingAdapter(List<Meeting> meetingList) {
            mMeetingList = meetingList;
        }

        @Override
        public MeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_meeting, parent, false);

            return new MeetingHolder(view);
        }

        @Override
        public void onBindViewHolder(MeetingHolder holder, int position) {
            Meeting meeting = mMeetingList.get(position);
            holder.bindMeeting(meeting);
        }

        @Override
        public int getItemCount() {
            return mMeetingList.size();
        }
    }


    public class MeetingTimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private MeetingTime mMeetingTime;

        public MeetingTimeHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.time_range_text_view);
            itemView.setOnClickListener(this);

        }

        public void bindTimeTable(MeetingTime meetingTime) {
            mMeetingTime = meetingTime;
            mTitleTextView.setText(mMeetingTime.getStartHour() + ":00" + " ~ " + mMeetingTime.getEndHour() + ":00");
        }

        @Override
        public void onClick(View v) {
            if (!mMeetingTime.isChecked())
                mMeetingTime.setChecked(true);
            else {
                mMeetingTime.setChecked(false);
            }
            meetingTimeAdapter.notifyDataSetChanged();

        }
    }

    public class MeetingTimeAdapter extends RecyclerView.Adapter<MeetingTimeHolder> {
        private List<MeetingTime> meetingTimeList;

        public MeetingTimeAdapter(List<MeetingTime> meetingTimeList) {
            this.meetingTimeList = meetingTimeList;
        }

        @Override
        public MeetingTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_time, parent, false);


            return new MeetingTimeHolder(view);
        }

        @Override
        public void onBindViewHolder(final MeetingTimeHolder holder, int position) {
            MeetingTime meetingTime = meetingTimeList.get(position);
            holder.bindTimeTable(meetingTime);

            if (meetingTime.isChecked())
                holder.mTitleTextView.setBackgroundResource(R.drawable.time_table_selected_background);
            else {
                holder.mTitleTextView.setBackgroundResource(R.drawable.time_table_unselected_background);
            }

        }

        @Override
        public int getItemCount() {
            return meetingTimeList.size();
        }

    }

    public static MeetingFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("MeetingFragment", teamProject);

        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
