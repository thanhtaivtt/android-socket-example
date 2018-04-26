package com.toidicode.socket;

/**
 * Created by thanhtai on 26/04/2018.
 */

public class Message {
    private String message;
    private boolean me;

    Message(String message, boolean me) {
        this.message = message;
        this.me = me;
    }

    String getMessage() {
        return message;
    }

    Boolean getMe() {
        return me;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", me=" + me +
                '}';
    }
}
