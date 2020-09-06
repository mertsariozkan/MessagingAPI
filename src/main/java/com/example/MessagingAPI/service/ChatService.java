package com.example.MessagingAPI.service;

import com.example.MessagingAPI.model.*;
import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.payload.response.GetConversationResponse;
import com.example.MessagingAPI.dao.ChatUsersDAO;
import com.example.MessagingAPI.dao.ConversationsDAO;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ConversationsDAO conversationsDAO;

    @Autowired
    private ChatUsersDAO chatUsersDAO;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);


    @Override
    public GenericResponse sendMessage(Message message) {
        Optional<ChatUser> optionalChatUser = chatUsersDAO.findByUsername(message.getFrom());

        if (!optionalChatUser.isPresent())
            return new GenericResponse(ResponseStatus.ERROR, "Chat user doesn't exist.");

        ChatUser chatUser = optionalChatUser.get();
        Chat chat = null;
        ArrayList<Chat> chats = chatUser.getChats();
        for (Chat c : chats) {
            if (c.getContact().equals(message.getTo())) {
                chat = c;
                break;
            }
        }
        Conversation savedConversation;

        if (chat != null) {
            if (chat.isBlocked())
                return new GenericResponse(ResponseStatus.ERROR, "User is blocked.");

            Optional<Conversation> optionalConversation = conversationsDAO.findConversationById(chat.getChatId());

            if (!optionalConversation.isPresent())
                return new GenericResponse(ResponseStatus.ERROR, "Conversation doesn't exist.");

            Conversation conversation = optionalConversation.get();
            List<Message> messages = conversation.getMessages();
            messages.add(message);
            conversation.setMessages(messages);
            savedConversation = conversationsDAO.save(conversation);

        } else {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(message);
            Conversation conversation = new Conversation(messages);
            savedConversation = conversationsDAO.save(conversation);
            chat = new Chat(conversation.getId(), message.getTo(), false, false);
            chats.add(chat);
            chatUser.setChats(chats);
            chatUsersDAO.save(chatUser);
        }

        logger.info("MESSAGE NOTIFICATION: 'New message from "+ message.getFrom() +"' notification is created for user '"+ message.getTo() +"'.");
        userService.createNewMessageNotification(chat.getChatId(), message.getTo());

        logger.info("MESSAGE SENT: '"+message.getTo()+"' has a new message from "+ message.getFrom() +".");
        return new GetConversationResponse(ResponseStatus.OK, message.getFrom(), message.getTo(), savedConversation);
    }

    @Override
    public ChatUser getAllChatDetails(String username, ChatFilter filter) {
        Optional<ChatUser> optionalChatUser = chatUsersDAO.findByUsername(username);
        if(!optionalChatUser.isPresent())
            return null;
        ChatUser chatUser = optionalChatUser.get();
        if(filter != null) {
            if(filter.getUsernameFilter() != null) {
                ArrayList<Chat> chats = new ArrayList<>();
                for(Chat c : chatUser.getChats()) {
                    if(c.getContact().startsWith(filter.getUsernameFilter()))
                        chats.add(c);
                }
                chatUser.setChats(chats);
            }
            int page = filter.getPage();
            ArrayList<Chat> chats = chatUser.getChats();
            Collections.reverse(chats);
            chats = (ArrayList<Chat>) chats.stream().skip((page-1)*10).limit(10).collect(Collectors.toList());
            chatUser.setChats(chats);
        }

        return chatUser;

    }

    @Override
    public Conversation getConversation(String username, String chatId, ChatFilter filter) {
        Optional<ChatUser> optionalChatUser = chatUsersDAO.findByUsername(username);
        if(!optionalChatUser.isPresent())
            return null;

        ChatUser chatUser = optionalChatUser.get();
        ArrayList<Chat> chats = chatUser.getChats();
        for(Chat chat : chats) {
            if(chat.getChatId().equals(chatId)) {
                chat.setHasNewMessage(false);
                break;
            }
        }
        chatUser.setChats(chats);
        chatUsersDAO.save(chatUser);

        Optional<Conversation> optionalConversation = conversationsDAO.findConversationById(chatId);
        if(!optionalConversation.isPresent())
            return null;
        Conversation conversation = optionalConversation.get();
        if(filter != null) {
            int page = filter.getPage();
            List<Message> messages = conversation.getMessages();
            Collections.reverse(messages);
            messages = messages.stream().skip((page-1)*10).limit(10).collect(Collectors.toList());

            conversation.setMessages(messages);
        }

        return conversation;

    }

}
