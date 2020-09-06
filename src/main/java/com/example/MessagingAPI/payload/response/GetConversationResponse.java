package com.example.MessagingAPI.payload.response;

import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.Conversation;

public class GetConversationResponse extends GenericResponse {

    private Conversation conversation;
    private String username;
    private String contactUsername;

    public GetConversationResponse(ResponseStatus status, String message) {
        super(status, message);
    }

    public GetConversationResponse(ResponseStatus status, String username, String contactUsername, Conversation conversation) {
        super(status);
        this.username = username;
        this.contactUsername = contactUsername;
        this.conversation = conversation;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
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
