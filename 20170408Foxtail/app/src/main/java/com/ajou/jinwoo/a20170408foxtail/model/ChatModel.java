package com.ajou.jinwoo.a20170408foxtail.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChatModel {

    private DatabaseReference ref;

    public ChatModel(){
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void sendMessage(String message){
        DatabaseReference childRef = ref.push();

        childRef.setValue(Chat.newChat(message));
    }
}


//github 계정만들기
//파이널 키워드의 용도
//불변객체를 사용했을때의 장점