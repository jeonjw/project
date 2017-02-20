package cc.foxtail.teamprojectmanager;


import java.util.Date;

public class Board {
    private Date mDate;
    private String mText;
    private String mWriter;

    public Date getDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }

    public String getWriter() {
        return mWriter;
    }

    public Board(Date date,String text,String writer){
        mDate = date;
        mText = text;
        mWriter = writer;
    }


}
