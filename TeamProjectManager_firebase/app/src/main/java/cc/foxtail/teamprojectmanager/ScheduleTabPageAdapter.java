package cc.foxtail.teamprojectmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ScheduleTabPageAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private TeamProject mTeamProject;

    public ScheduleTabPageAdapter(FragmentManager fm, int tabCount,TeamProject teamProject) {
        super(fm);
        this.tabCount = tabCount;
        this.mTeamProject = teamProject;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                return MeetingRootFragment.newInstance(mTeamProject);
            }
            case 1: {
                return AssignmentRootFragment.newInstance(mTeamProject);
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
