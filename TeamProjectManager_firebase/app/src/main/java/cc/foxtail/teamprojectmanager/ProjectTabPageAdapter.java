package cc.foxtail.teamprojectmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ProjectTabPageAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private TeamProject mTeamProject;

    public ProjectTabPageAdapter(FragmentManager fm, int tabCount, TeamProject teamProject) {
        super(fm);
        this.tabCount = tabCount;
        this.mTeamProject = teamProject;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ScheduleFragment.newInstance(mTeamProject);
            case 1:
                return new FileShareFragment();
            case 2:
                return TeamAchievementFragment.newInstance(mTeamProject);
            case 3:
                return BoardFragment.newInstance(mTeamProject);
            case 4:
                return ChattingFragment.newInstance(mTeamProject.getTitle());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
