package cc.foxtail.teamprojectmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamProject implements Parcelable {

    private UUID mId;
    private String mTitle;
    private List<Board> mBoardList;
    private List<Meeting> mMeetingList;
    private List<Assignment> mAssignmentList;


    public void setId(UUID id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public TeamProject() {
        mId = UUID.randomUUID();
        mBoardList = new ArrayList<>();
        mMeetingList = new ArrayList<>();
        mAssignmentList = new ArrayList<>();
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
