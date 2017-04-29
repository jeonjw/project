package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;
import java.util.StringTokenizer;

public class TeamAchievementFragment extends Fragment {
    private TeamProject mTeamProject;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        mTeamProject = getArguments().getParcelable("Team_Achievement_Fragment");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.achievement_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        updateUI();

        return view;
    }

    private void updateUI() {
        AchievementAdapter achievementAdapter = new AchievementAdapter(mTeamProject.getAssignmentList());
        mRecyclerView.setAdapter(achievementAdapter);
        achievementAdapter.notifyDataSetChanged();
    }

    public class AchievementHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mTitleTextView;
        private Assignment mAssignment;

        public AchievementHolder(View itemView) {
            super(itemView);

            mDateTextView = (TextView) itemView.findViewById(R.id.achievement_assignment_date);
            mTitleTextView = (TextView) itemView.findViewById(R.id.achievement_assignment_title);

        }

        public void bindAssignment(Assignment assignment) {
            mAssignment = assignment;
            mDateTextView.setText(dateToString());
            mTitleTextView.setText(mAssignment.getAssignmentTitle());
        }

        //        private String dateToString() {
//            CalendarDay startDate = mAssignment.getDateList().get(0);
//            CalendarDay endDate = mAssignment.getDateList().get(mAssignment.getDateList().size() - 1);
//            String sDate = String.valueOf(startDate.getYear() + "." + (startDate.getMonth()+1) + "." + startDate.getDay());
//            String eDate = " - " + String.valueOf((endDate.getMonth()+1) + "." + endDate.getDay());
//
//            return sDate + eDate;
//        }
        private String dateToString() {
            String startDate = mAssignment.getDateList().get(0);
            StringTokenizer startDateTokenizer = new StringTokenizer(startDate);
            String startYear = startDateTokenizer.nextToken(".");
            String startMonth = startDateTokenizer.nextToken(".");
            String startDay = startDateTokenizer.nextToken(".");

            startDate = startYear + "년 " + startMonth + "월 " + startDay + "일";

            for (int i = 1; i < mAssignment.getDateList().size(); i++) {
                String endDate = mAssignment.getDateList().get(i);
                StringTokenizer endDateTokenizer = new StringTokenizer(endDate);
                endDateTokenizer.nextToken(".");
                String endMonth = endDateTokenizer.nextToken(".");
                String endDay = endDateTokenizer.nextToken(".");

                startDate += ", " + endMonth + "월 " + endDay + "일";
            }

            return startDate;
        }

    }

    public class AchievementAdapter extends RecyclerView.Adapter<AchievementHolder> {
        private List<Assignment> mAssignmentList;

        public AchievementAdapter(List<Assignment> assignmentList) {
            mAssignmentList = assignmentList;
        }

        @Override
        public AchievementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_achievement, parent, false);

            return new AchievementHolder(view);
        }

        @Override
        public void onBindViewHolder(AchievementHolder holder, int position) {
            Assignment assignment = mAssignmentList.get(position);
            holder.bindAssignment(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignmentList.size();
        }
    }

    public static TeamAchievementFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("Team_Achievement_Fragment", teamProject);

        TeamAchievementFragment fragment = new TeamAchievementFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
