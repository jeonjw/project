package cc.foxtail.teamprojectmanager;

public class Board {
    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public void setWriter(String mWriter) {
        this.mWriter = mWriter;
    }

    private String mDate;
    private String mText;
    private String mWriter;

    public Board() {

    }

    public String getDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }

    public String getWriter() {
        return mWriter;
    }

    public Board(String date, String text, String writer) {
        this.mDate = date;
        mText = text;
        mWriter = writer;
    }


}
