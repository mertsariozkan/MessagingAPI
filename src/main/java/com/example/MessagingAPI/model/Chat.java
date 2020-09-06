package com.example.MessagingAPI.model;


public class Chat {

    private String chatId;
    private String contact;
    private boolean hasNewMessage;
    private boolean isBlocked;

    public Chat(String chatId, String contact, boolean hasNewMessage, boolean isBlocked) {
        this.chatId = chatId;
        this.contact = contact;
        this.hasNewMessage = hasNewMessage;
        this.isBlocked = isBlocked;
    }

    public Chat() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isHasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
