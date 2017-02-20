package cc.foxtail.teamprojectmanager;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

public class Assignment {
    private String assignmentTitle;
    private String assignmentDetails;
    private List<CalendarDay> dateList;

    public String getAssignmentDetails() {
        return assignmentDetails;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public Assignment(String title, String details, List<CalendarDay> dateList) {
        this.assignmentTitle = title;
        this.assignmentDetails = details;
        this.dateList = dateList;
    }

    public List<CalendarDay> getDateList() {
        return dateList;
    }

}
