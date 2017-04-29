package com.ajou.jinwoo.a20170415foxtail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatModel {
    private DatabaseReference ref;
    private List<Chat> chats = new ArrayList<>();
    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public ChatModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.ref = database.getReference();
        this.ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Chat> newChats = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot e : children) {
                    Chat chat = e.getValue(Chat.class);
                    newChats.add(chat);
                }

                chats = newChats;
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public void sendMessage(String message) {
        DatabaseReference childRef = ref.push();

        childRef.setValue(Chat.newChat(message));
    }

    public String getMessage(int position) {
        return chats.get(position).message;
    }

    public int getMessageCount() {
        return chats.size();
    }
}

class Chat {
    final String message;
    final String timestamp;

    public Chat() {
        this.message = "";
        this.timestamp = "";
    }

    public Chat(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    static public Chat newChat(String message) {
        return new Chat(message, timeStamp());
    }

    private static String timeStamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("a h:mm", Locale.KOREA);

        return dateFormat.format(date);
    }


}