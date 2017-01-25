package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public class ProjectFragment extends Fragment {


    private ViewPager mViewPager;
    public static final String ARG_PROJECT_ID =
            "cc.foxtail.teamproject.projcet_id";
    private TeamProject mTeamProject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("일정 관리"));
        tabLayout.addTab(tabLayout.newTab().setText("파일 공유"));
        tabLayout.addTab(tabLayout.newTab().setText("팀원 성취도"));
        tabLayout.addTab(tabLayout.newTab().setText("게시판"));
        tabLayout.addTab(tabLayout.newTab().setText("채팅"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getFragmentManager(), tabLayout.getTabCount(),mTeamProject);
        mViewPager.setAdapter(tabPagerAdapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionbar_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID projectId = (UUID) getArguments().getSerializable(ARG_PROJECT_ID);
        mTeamProject = TeamProjectLab.get().getTeamProject(projectId);
    }

    public static ProjectFragment newInstance(UUID projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID, projectId);

        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
