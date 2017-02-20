package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.List;


public class ScheduleFragment extends Fragment implements OnDateSelectedListener , OnRangeSelectedListener{

    private ViewPager mViewPager;
    public static final String ARG_TEAM_PROJECT =
            "cc.foxtail.teamproject.team_project";
    private MaterialCalendarView materialCalendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.schedule_tab_layout);

        TeamProject mTeamProject = getArguments().getParcelable(ARG_TEAM_PROJECT);

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);


        tabLayout.setupWithViewPager(mViewPager);

        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setOnDateChangedListener(this);
        materialCalendarView.setOnRangeSelectedListener(this);


        tabLayout.addTab(tabLayout.newTab().setText("모임"));
        tabLayout.addTab(tabLayout.newTab().setText("과제"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) view.findViewById(R.id.schedule_view_pager);

        ScheduleTabPageAdapter scheduleTabPageAdapter =
                new ScheduleTabPageAdapter(getChildFragmentManager(), tabLayout.getTabCount(), mTeamProject);

        mViewPager.setAdapter(scheduleTabPageAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }

    public static ScheduleFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TEAM_PROJECT, teamProject);

        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        AddMeetingFragment addMeetingFragment = (AddMeetingFragment)
                getChildFragmentManager().findFragmentByTag("AddMeetingFragment");

        if (addMeetingFragment != null) {
            addMeetingFragment.setSelectedDateList(materialCalendarView.getSelectedDates());
        }

    }
    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        AddAssignmentFragment addAssignmentFragment = (AddAssignmentFragment)
                getChildFragmentManager().findFragmentByTag("AddAssignmentFragment");

        if(addAssignmentFragment != null){
            addAssignmentFragment.setSelectedDateList(dates);
        }

    }


    public void clearSelection() {
        materialCalendarView.clearSelection();
    }
    public void setSelectMode(int mode){
        materialCalendarView.setSelectionMode(mode); //모드 선택 버그 있음.
    }


}
