package cc.foxtail.teamprojectmanager;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Assignment {
    private String assignmentTitle;
    private String assignmentDetails;
    private List<String> dateList;

    public Assignment(){

    }

    public String getAssignmentDetails() {
        return assignmentDetails;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public Assignment(String title, String details, List<String> dateList) {
        this.assignmentTitle = title;
        this.assignmentDetails = details;
        this.dateList = dateList;
    }

    public List<String> getDateList() {
        return dateList;
    }



}
