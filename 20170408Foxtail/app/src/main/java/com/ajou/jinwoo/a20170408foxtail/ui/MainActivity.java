package com.ajou.jinwoo.a20170408foxtail.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ajou.jinwoo.a20170408foxtail.R;
import com.ajou.jinwoo.a20170408foxtail.model.Chat;
import com.ajou.jinwoo.a20170408foxtail.model.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private EditText editText;
    private Button button;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ChatModel model = new ChatModel();
        chatList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.send_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.sendMessage(editText.getText().toString());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(chatList);
        mRecyclerView.setAdapter(chatAdapter);
        loadNoticeData();

    }
    private void loadNoticeData() {
        mDatabase.limitToLast(1).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private class ChatHolder extends RecyclerView.ViewHolder {
        private TextView mMessageTextView;
        private TextView mTimeTextView;
        private Chat chat;


        public ChatHolder(View itemView) {
            super(itemView);

            mMessageTextView = (TextView) itemView.findViewById(R.id.message_text_view);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
        }


        private void bindNotice(Chat studentNotice) {
            chat = studentNotice;
            mMessageTextView.setText(chat.getMessage());
            mTimeTextView.setText(chat.getTimestamp());

        }
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
        private List<Chat> chatList;

        public ChatAdapter(List<Chat> chatList) {
            this.chatList = chatList;
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            final View view = layoutInflater.inflate(R.layout.list_item_chat, parent, false);

            return new ChatHolder(view);
        }

        @Override
        public void onBindViewHolder(final ChatHolder holder, int position) {
            Chat chat = chatList.get(position);
            holder.bindNotice(chat);
        }

        @Override
        public int getItemCount() {
            return chatList.size();
        }
    }
}

// Java Native Interface ==> C/C++로 만든걸 자바에서 사용가능
// Open Source ==> License 잘 확인하고 사용해야 자신이 사용한 라이센스를 애플리케이션에 명시
// GPL License 통해 만든 앱은 소스를 공개해야한다.
// GNU C 를 사용하면 코드를 공개해야하지만 안드로이드는 구글에서만든 Bionic C "APACHE"라이센스사용해서 코드공개 안해도 된다
// 안드로이드에서 JNI 사용하는걸 NDK 라고한다.




// java 만든사람 "제임스 고슬링"
// java Me 라는걸로 모바일머신을 만들 수 있었지만 성능상 문제가 많았다.

// 리눅스커널=> bionic c/c++ ==> android framework (c++) ==> java 계층으로 안드로이드가 이루어져있다.
//framework ==> 엔진과의 큰 차이는 프레임웍에는 흐름이 존재한다(~하려면 ~를 해야한다.)
//engine ==> 라이브러르의 집합
//library ==> class와 함수의 집함


// Compiler (Code ==> 기계어) Assemble 과정을 통해
// Os(운영체제, Operation System)
// ==> 리소스 관리 (물리적, 추상적) , 프로그램을 구동할 수 있는 기반 환경(플랫폼)
// 오픈소스 - 리챠드 스톨만(FSF - GPL) 이사람 재단에 GCC, C library
// ==> 위 재단의 리눅스를 GNU(GNU IS NOT UNIX) LINUX

//컨스트레인트 레이아웃 등장 이유 : 화면해상도가 기기마다 다른걸 해결해주기 위해 등장