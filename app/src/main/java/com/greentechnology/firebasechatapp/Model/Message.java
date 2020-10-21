package com.greentechnology.firebasechatapp.Model;

public class Message {

    private String message, receiverId, senderId, type;

    public Message() {
    }



    public Message(String message, String receiverId, String senderId, String type) {
        this.message = message;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
