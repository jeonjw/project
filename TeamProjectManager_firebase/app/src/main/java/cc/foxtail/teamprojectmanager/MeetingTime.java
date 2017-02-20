package cc.foxtail.teamprojectmanager;

public class MeetingTime {
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    private int startHour;
    private int endHour;

    public void addCount() {
        this.count++;
    }

    public int getCount() {
        return count;
    }

    private int count;

    public boolean isChecked() {
        return isChecked;
    }

    private boolean isChecked;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public MeetingTime(int startHour, int endHour){
        this.startHour = startHour;
        this.endHour = endHour;
    }


}
