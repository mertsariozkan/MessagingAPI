package com.example.MessagingAPI.payload.request;

import com.example.MessagingAPI.model.ChatFilter;

public class GetConversationRequest {
    private String chatId;
    private String username;
    private String contactUsername;
    private ChatFilter filter;

    public ChatFilter getFilter() {
        return filter;
    }

    public void setFilter(ChatFilter filter) {
        this.filter = filter;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }
}
