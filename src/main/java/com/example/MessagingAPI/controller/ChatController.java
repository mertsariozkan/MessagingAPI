package com.example.MessagingAPI.controller;

import com.example.MessagingAPI.model.ChatFilter;
import com.example.MessagingAPI.payload.request.GetAllChatDetailsRequest;
import com.example.MessagingAPI.payload.request.GetConversationRequest;
import com.example.MessagingAPI.payload.request.SendMessageRequest;
import com.example.MessagingAPI.payload.response.GetAllChatDetailsResponse;
import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.payload.response.GetConversationResponse;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.ChatUser;
import com.example.MessagingAPI.model.Conversation;
import com.example.MessagingAPI.model.Message;
import com.example.MessagingAPI.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/all")
    public GenericResponse getAllChatDetails(@RequestBody GetAllChatDetailsRequest request) {
        if(request.getFilter() == null)
            request.setFilter(new ChatFilter());

        ChatUser chatUser = chatService.getAllChatDetails(request.getUsername(), request.getFilter());

        if (chatUser == null)
            return new GenericResponse(ResponseStatus.ERROR, "Inbox couldn't retrieved.");

        return new GetAllChatDetailsResponse(ResponseStatus.OK, chatUser);
    }

    @GetMapping
    public GetConversationResponse getConversation(@Valid @RequestBody GetConversationRequest request) {
        if(request.getFilter() == null)
            request.setFilter(new ChatFilter());

        Conversation conversation = chatService.getConversation(request.getUsername(), request.getChatId(), request.getFilter());
        if (conversation == null)
            return new GetConversationResponse(ResponseStatus.ERROR, "Conversation couldn't retrieved.");

        return new GetConversationResponse(ResponseStatus.OK,
                request.getUsername(),
                request.getContactUsername(),
                conversation);
    }

    @PostMapping
    public GenericResponse sendMessage(@Valid @RequestBody SendMessageRequest request) {
        Message message = new Message(request.getFrom(), request.getTo(), request.getContent(), LocalDateTime.now());
        return chatService.sendMessage(message);
    }
}
