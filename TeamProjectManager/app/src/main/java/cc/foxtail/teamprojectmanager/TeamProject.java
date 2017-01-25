package cc.foxtail.teamprojectmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamProject {
    private UUID mId;
    private String mTitle;
    private List<Board> mBoardList;

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
    }

    public List<Board> getBoardList() {
        return mBoardList;
    }
}
