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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Board> mBoardList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        updateUI();

        final EditText writeEditText = (EditText) view.findViewById(R.id.write_edit_text);
        Button writeButton = (Button) view.findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                Board board = new Board(date, writeEditText.getText().toString(), "전진우");
                mBoardList.add(board);
                writeEditText.setText(null);
                Toast.makeText(getActivity(),"작성 완료",Toast.LENGTH_SHORT).show();
                updateUI();

            }
        });


        return view;
    }

    public void setBoardList(List<Board> boardList) {
        mBoardList = boardList;

    }

    private void updateUI() {


        BoardAdapter adapter = new BoardAdapter(mBoardList);
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
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(mBoard.getDate());
            mDateTextView.setText(dateString);
            mContextTextView.setText(mBoard.getText());
        }
    }

}
