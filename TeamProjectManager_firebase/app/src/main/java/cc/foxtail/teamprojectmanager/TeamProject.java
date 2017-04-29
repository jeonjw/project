package cc.foxtail.teamprojectmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamProject implements Parcelable, Serializable {

    private String mTitle;

    public void setMeetingList(List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
    }

    public void setBoardList(List<Board> boardList) {
        this.mBoardList = boardList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.mAssignmentList = assignmentList;
    }

    public void setUserList(List<String> userList){
        this.mUserList = userList;
    }


    private List<Meeting> mMeetingList;
    private List<Assignment> mAssignmentList;
    private List<Board> mBoardList;
    private List<String> mUserList;

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public TeamProject() {
        mBoardList = new ArrayList<>();
        mMeetingList = new ArrayList<>();
        mAssignmentList = new ArrayList<>();
        mUserList = new ArrayList<>();
    }

    public List<Board> getBoardList() {
        return mBoardList;
    }

    public List<Meeting> getMeetingList() {
        return mMeetingList;
    }

    public List<Assignment> getAssignmentList() {
        return mAssignmentList;
    }

    public List<String> getUserList() { return mUserList;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
    }

    protected TeamProject(Parcel in) {
        mTitle = in.readString();
    }

    public static final Creator<TeamProject> CREATOR = new Creator<TeamProject>() {
        @Override
        public TeamProject createFromParcel(Parcel in) {
            return new TeamProject(in);
        }

        @Override
        public TeamProject[] newArray(int size) {
            return new TeamProject[size];
        }
    };
}
