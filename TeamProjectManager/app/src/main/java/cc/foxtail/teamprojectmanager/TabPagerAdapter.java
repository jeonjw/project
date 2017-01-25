package cc.foxtail.teamprojectmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private TeamProject mTeamProject;

    public TabPagerAdapter(FragmentManager fm, int tabCount,TeamProject teamProject) {
        super(fm);
        this.tabCount = tabCount;
        mTeamProject = teamProject;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ScheduleFragment();
            case 1:
                return new FileShareFragment();
            case 2:
                return new TeamAchievementFragment();
            case 3: {
                BoardFragment boardFragment = new BoardFragment();
                boardFragment.setBoardList(mTeamProject.getBoardList());

                return boardFragment;
            }
            case 4:
                return new ChattingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
