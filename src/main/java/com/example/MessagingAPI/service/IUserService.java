package com.example.MessagingAPI.service;

import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.model.ChatUser;

import java.util.List;

public interface IUserService {
    void createNewMessageNotification(String chatId, String username);
    GenericResponse blockUser(String blockingUser, String blockedUser);
    GenericResponse unblockUser(String blockingUser, String blockedUser);
    List<String> searchUser(String username);
    ChatUser createChatUser(String username);
}
