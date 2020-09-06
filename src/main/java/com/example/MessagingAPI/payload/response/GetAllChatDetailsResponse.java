package com.example.MessagingAPI.payload.response;

import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.ChatUser;

public class GetAllChatDetailsResponse extends GenericResponse {

    private ChatUser chatUser;

    public GetAllChatDetailsResponse(ResponseStatus status, String message) {
        super(status, message);
    }

    public GetAllChatDetailsResponse(ResponseStatus status, ChatUser chatUser) {
        super(status);
        this.chatUser = chatUser;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }
}
