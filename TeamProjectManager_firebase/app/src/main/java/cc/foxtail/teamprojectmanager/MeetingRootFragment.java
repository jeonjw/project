package cc.foxtail.teamprojectmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MeetingRootFragment extends Fragment {
    private TeamProject mTeamProject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_root, container, false);

        mTeamProject = getArguments().getParcelable("MeetingRootFragment");
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.meeting_root_frame, MeetingFragment.newInstance(mTeamProject)).commit();


        return view;
    }

    public static MeetingRootFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("MeetingRootFragment", teamProject);

        MeetingRootFragment fragment = new MeetingRootFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
