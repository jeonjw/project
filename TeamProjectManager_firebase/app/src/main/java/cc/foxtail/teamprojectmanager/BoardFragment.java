package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TeamProject mTeamProject;
    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler_view);

        mTeamProject = getArguments().getParcelable("Board_Fragment");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);


        final EditText writeEditText = (EditText) view.findViewById(R.id.write_edit_text);
        Button writeButton = (Button) view.findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                Board board = new Board(dateToString(date), writeEditText.getText().toString(), "전진우");
                mTeamProject.getBoardList().add(board);
                writeEditText.setText(null);
                Toast.makeText(getActivity(), "작성 완료", Toast.LENGTH_SHORT).show();

                Query query = mDatabase.child("TeamProject").orderByChild("title").equalTo(mTeamProject.getTitle());
                query.getRef().child(mTeamProject.getTitle()).setValue(mTeamProject);

            }
        });

        updateUI();

        return view;
    }

    private String dateToString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private void updateUI() {
        BoardAdapter adapter = new BoardAdapter(mTeamProject.getBoardList());
        mRecyclerView.setAdapter(adapter);
    }

    private class BoardAdapter extends RecyclerView.Adapter<BoardHolder> {
        private List<Board> mBoardList;

        public BoardAdapter(List<Board> boardList) {
            mBoardList = boardList;
        }

        @Override
        public BoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_board, parent, false);


            return new BoardHolder(view);
        }

        @Override
        public void onBindViewHolder(BoardHolder holder, int position) {
            Board board = mBoardList.get(position);
            holder.bindBoard(board);
        }

        @Override
        public int getItemCount() {
            return mBoardList.size();
        }
    }

    private class BoardHolder extends RecyclerView.ViewHolder {
        private TextView mWriterTextView;
        private TextView mDateTextView;
        private TextView mContextTextView;
        private Board mBoard;


        public BoardHolder(View itemView) {
            super(itemView);

            mWriterTextView = (TextView) itemView.findViewById(R.id.board_writer_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.board_date_text_view);
            mContextTextView = (TextView) itemView.findViewById(R.id.board_context_text_view);
        }


        public void bindBoard(Board board) {
            mBoard = board;
            mWriterTextView.setText(mBoard.getWriter());
            mDateTextView.setText(mBoard.getDate());
            mContextTextView.setText(mBoard.getText());
        }
    }

    public static BoardFragment newInstance(TeamProject teamProject) {
        Bundle args = new Bundle();
        args.putParcelable("Board_Fragment", teamProject);

        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
