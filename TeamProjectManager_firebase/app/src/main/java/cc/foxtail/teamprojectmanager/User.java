package cc.foxtail.teamprojectmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String mUserName;
    private String mUserEmail;
    private List<String> mTeamProjectList;

    protected User(Parcel in) {
        mUserName = in.readString();
        mUserEmail = in.readString();
        mTeamProjectList = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.mUserEmail = userEmail;
    }

    public void setTeamProjectList(List<String> teamProjectList) {
        this.mTeamProjectList = teamProjectList;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public List<String> getTeamProjectList() {
        return mTeamProjectList;
    }


    public User(){
        mTeamProjectList = new ArrayList<>();
    }
    public User(String userName, String userEmail){
        mTeamProjectList = new ArrayList<>();
        mUserName = userName;
        mUserEmail = userEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
        dest.writeString(mUserEmail);
        dest.writeStringList(mTeamProjectList);
    }
}
