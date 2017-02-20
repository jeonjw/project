package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AssignmentRootFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_root, container, false);

        TeamProject mTeamProject = getArguments().getParcelable("MeetingRootFragment");
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.assign_root_frame, AssignmentFragment.newInstance(mTeamProject)).commit();


        return view;
    }

    public static AssignmentRootFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("MeetingRootFragment", teamProject);

        AssignmentRootFragment fragment = new AssignmentRootFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
