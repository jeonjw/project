package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

public class AssignmentFragment extends Fragment{

    private TeamProject mTeamProject;
    private AssignmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment,container,false);

        final ScheduleFragment scheduleFragment = (ScheduleFragment) getParentFragment();
        scheduleFragment.clearSelection();
        //scheduleFragment.setSelectMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        mTeamProject = getArguments().getParcelable("AssignmentFragment");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.assignment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);



        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.floating_assignment_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleFragment.setSelectMode(MaterialCalendarView.SELECTION_MODE_RANGE);

                Fragment fragment = new AddAssignmentFragment();
                fragment.setTargetFragment(AssignmentFragment.this, -1);
                FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.assign_root_frame, fragment, "AddAssignmentFragment").addToBackStack(null).commit();
            }
        });

        adapter = new AssignmentAdapter(mTeamProject.getAssignmentList());
        mRecyclerView.setAdapter(adapter);


        return view;
    }

    public void addAssignment(Assignment assignment) {
        mTeamProject.getAssignmentList().add(assignment);
        adapter.notifyDataSetChanged();
    }

    private class AssignmentHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private Assignment mAssignment;


        public AssignmentHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.assignment_title_text_view);
        }


        public void bindAssignment(Assignment assignment) {
            mAssignment = assignment;
            mTitleTextView.setText(mAssignment.getAssignmentTitle());
        }


    }

    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder> {
        private List<Assignment> mAssignmentList;

        public AssignmentAdapter(List<Assignment> assignmentList) {
            mAssignmentList = assignmentList;
        }

        @Override
        public AssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_assignment, parent, false);

            return new AssignmentHolder(view);
        }

        @Override
        public void onBindViewHolder(AssignmentHolder holder, int position) {
            Assignment assignment = mAssignmentList.get(position);
            holder.bindAssignment(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignmentList.size();
        }
    }

    public static AssignmentFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("AssignmentFragment", teamProject);

        AssignmentFragment fragment = new AssignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
