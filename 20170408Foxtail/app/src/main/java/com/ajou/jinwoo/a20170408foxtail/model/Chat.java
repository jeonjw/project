package com.ajou.jinwoo.a20170408foxtail.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Chat {

    private final String message;
    private final String timestamp;

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    Chat(String timestamp, String message) {
        this.message = message;
        this.timestamp = timestamp;
    }

    static Chat newChat(String message) {
        return new Chat(message, timestamp());
    }

    private static String timestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("a h:mm", Locale.KOREA);

        return dateFormat.format(date);
    }

}
