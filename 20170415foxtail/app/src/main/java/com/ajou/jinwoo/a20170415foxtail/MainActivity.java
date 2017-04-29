package com.ajou.jinwoo.a20170415foxtail;

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


public class MainActivity extends AppCompatActivity {
    private ChatModel chatModel = new ChatModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText chatEdit = (EditText) findViewById(R.id.edit_text);

        Button chatButton = (Button) findViewById(R.id.send_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = chatEdit.getText().toString();
                if (msg.length() > 0) {
                    chatModel.sendMessage(msg);
                    chatEdit.setText("");
                }
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // CRTP = Curiosly Recurring Template Pattern (C++ 용어)
        // = 부모에게 자기 자신의 타입을 Generic 인자로 넘기는 형식의 설계 방법을 의미한다.
        recyclerView.setAdapter(new RecyclerView.Adapter<ChatHolder>() {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.list_item_chat, parent, false);
                return new ChatHolder(view);
            }

            @Override
            public void onBindViewHolder(ChatHolder holder, int position) {
                String message = chatModel.getMessage(position);
                holder.setText(message);
            }

            @Override
            public int getItemCount() {
                return chatModel.getMessageCount();
            }
        });

        chatModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}

class ChatHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public ChatHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.message_text_view);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
//static final int a 에서 static 을 쓰는 이유는 어처피 변하지 않을꺼니까 하나만 생성되게끔하는거다 static 을 안쓰면 클래스 단위로 a라는 변수가 계속 생기는데
//static 으로 만들면 하나만 생성 된다.
//레퍼런스 타입과 밸류타입 조사해보기.


// Value Type 과 Reference Type 이란?
// primitive type - Java ,built-in type - C

// Reference Type ==> heap에 생성된다

// 얕은복사 ==> 문제점은 댕글링 포인터의 문제 (해결방법 : 참조계수)  //깊은복사
// 참조계수 reference Counting 모든 객체는 자신이 가리키고 있는 참조를 카운트한다 . 참조하는 카운트가 0일때 객체를 파괴한다.
// 참조 계수 문제 1. 상호 참조시 메모리 누수 문제(약한 참조로 문제 해결) 2. 카운팅 증감에따른(원자적으로 작동하도록 동기화) 오버헤드.
// 강한참조 약한참조 ?
// 강한 참조는 참조하고 있는동안 레퍼런스 카운팅을 증감, 약한참조는 카운팅을 증감하지않고 그냥 참조만 하고있는것이다.
// 약한참조는 참조계수를 증감하지 않으니까 댕글링 포인트 문제가 또 발생할 수 있다. 그래서 추가적인 기능을 제공하는데
// 이 객체의 유효성 검사를 할 수 있도록 Auto nulling 기능을 제공(객체가 유효하지 않으면 자동으로 null 로 변환).

// 강한참조 약한참조를 판단하는 기준은 소유권 객체를 생성 삭제에 대한 소유권을 가질 수 있다면 강한참조 아니면 약한 참조
// 약한참조를 사용하기 좋은 상황 : cache 같은거 약한참조 기반으로 만든다 (WeakHashMap 을 통해서 캐쉬를 만든다)
// 약한참조 만드는 법 WeakReference : 수거 대상이 되면 무조건 수거한다
//               SoftReference : 아웃오브 메모리에 근접했을때 수거한다.

// 참조계수를 구현하는 기법은 1. Garbage Collection  C# ,JAVA
//  ==> 별도의 프로그램이 객체의 참조 계수를 관리한다 Runtime (단점: 파괴(수거) 시점이 명확하지 않다.)

// 2. ARC (Auto reference Counting) - Swift
//  ==> 컴파일러가 코드를 분석해서 참조 계수 관련 코드를 삽입 해준다. Compile time

//Value type ==> static , stack, heap 에 생성될 수 있다.. (빠르게 생성할 수 있고 아래와 같이 내가 정해서 사용할 수 있다.)

// heap : 사용자가 원하는 시점에 생성할 수 있다고 파괴할 수 있다.
// 단점 : 동적메모리 할당은 내부적으로 복잡한 시퀀스가 있다 ==> 이 복잡한 시퀀스 때문에 성능 저하가 올 수 있다.
// stack : 함수가 시작될때 생성되고 함수가 끝날때 파괴된다
// 장점 : 생성과 파괴의 비용이 거의 없다.
// static : 프로그램이 시작할때 생성되고, 프로그램이 종료할 때 파괴된다.
// 장점 : 빠르다 , 단점 : 프로그램 용량이 커질 수 있다.

// compile language : 빠르긴한데 ,컴파일 시간에대한 문제
// 콜백이란 이벤트에 대한 처리를 하기위한 핸들러 누군가에 의해서 자동적으로 호출되는애
