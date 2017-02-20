package cc.foxtail.teamprojectmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamProjectLab {
    private static TeamProjectLab sTeamProjectLab;

    private List<TeamProject> mTeamProjectList;

    public static TeamProjectLab get(){
        if(sTeamProjectLab == null){
            sTeamProjectLab = new TeamProjectLab();
        }
        return sTeamProjectLab;
    }

    private TeamProjectLab(){
        mTeamProjectList = new ArrayList<>();


        for(int i = 0; i < 3; i++){
            TeamProject teamProject = new TeamProject();
            teamProject.setTitle("팀플 #"+ i);
            mTeamProjectList.add(teamProject);
        }
    }

    public List<TeamProject> getTeamProjectList() {
        return mTeamProjectList;
    }

    public TeamProject getTeamProject(UUID id){
        for(TeamProject teamProject : mTeamProjectList){
            if(teamProject.getId().equals(id)){
                return teamProject;
            }
        }
        return null;
    }
}
