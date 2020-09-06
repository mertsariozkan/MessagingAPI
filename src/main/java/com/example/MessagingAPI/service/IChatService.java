package com.example.MessagingAPI.service;

import com.example.MessagingAPI.model.ChatFilter;
import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.model.ChatUser;
import com.example.MessagingAPI.model.Conversation;
import com.example.MessagingAPI.model.Message;

public interface IChatService {
    GenericResponse sendMessage(Message message);
    ChatUser getAllChatDetails(String username, ChatFilter filter);
    Conversation getConversation(String username, String chatId, ChatFilter filter);
}
