package com.fc.samples.restservice;

import java.util.Formatter;

public class Message {

    private String message;

    public static Message build(String format, Object... args) {
        return new Message(new Formatter().format(format, args).toString());
    }

    private Message() {
        this("nomessage");
    }

    private Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
