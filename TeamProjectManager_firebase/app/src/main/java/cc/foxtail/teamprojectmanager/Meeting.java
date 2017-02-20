package cc.foxtail.teamprojectmanager;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

public class Meeting {

    private List<CalendarDay> dateList;
    private List<MeetingTime> meetingTimeList;
    private int userNum;

    public int getVoteUserNum() {
        return voteUserNum;
    }

    private int voteUserNum;

    public List<CalendarDay> getDateList() {
        return dateList;
    }

    public Meeting(List<CalendarDay> dateList, List<MeetingTime> meetingTimeList) {
        this.dateList = dateList;
        this.meetingTimeList = meetingTimeList;
        userNum = 3;
        voteUserNum = 0;
    }

    public List<MeetingTime> getMeetingTimeList() {
        return meetingTimeList;
    }

    public void selectMeetingTime(List<MeetingTime> selectedTimeList) {
        for (int i = 0; i < selectedTimeList.size(); i++) {
            for (int j = 0; j < meetingTimeList.size(); j++)
                if (meetingTimeList.get(j).getStartHour() == selectedTimeList.get(i).getStartHour())
                    meetingTimeList.get(j).addCount();
        }
        voteUserNum++;
    }

    public MeetingTime getElectedMeetingTime() {
        int max = 0;
        int position = 0;
        for (int i = 0; i < meetingTimeList.size(); i++) {
            MeetingTime meetingTime = meetingTimeList.get(i);
            if (meetingTime.getCount() > max) {
                max = meetingTime.getCount();
                position = i;
            }
        }

        return meetingTimeList.get(position);
    }
}
