package cc.foxtail.teamprojectmanager;

public class Invitation {
    private String mSenderName;
    private String mTeamProjectTitle;

    public void setSenderName(String mSenderName) {
        this.mSenderName = mSenderName;
    }

    public void setTeamProjectTitle(String mTeamProjectTitle) {
        this.mTeamProjectTitle = mTeamProjectTitle;
    }

    public String getSenderName() {
        return mSenderName;
    }

    public String getTeamProjectTitle() {
        return mTeamProjectTitle;
    }

    public Invitation(){

    }
    public Invitation(String senderName,String teamProjectTitle){
        mSenderName = senderName;
        mTeamProjectTitle = teamProjectTitle;
    }
}
