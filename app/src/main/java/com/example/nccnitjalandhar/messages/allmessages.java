package com.example.nccnitjalandhar.messages;

public class allmessages {
    String sender,reciever,message;
private  boolean seen;
    public allmessages() {
    }

    public allmessages(String sender, String reciever, String message,boolean seen) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
