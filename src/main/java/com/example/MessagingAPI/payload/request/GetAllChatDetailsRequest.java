package com.example.MessagingAPI.payload.request;

import com.example.MessagingAPI.model.ChatFilter;

public class GetAllChatDetailsRequest {
    private String username;
    private ChatFilter filter;

    public ChatFilter getFilter() {
        return filter;
    }

    public void setFilter(ChatFilter filter) {
        this.filter = filter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
