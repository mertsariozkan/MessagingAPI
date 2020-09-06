package com.example.MessagingAPI.service;

import com.example.MessagingAPI.dao.ChatUsersDAO;
import com.example.MessagingAPI.dao.ConversationsDAO;
import com.example.MessagingAPI.model.*;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @MockBean
    private ChatUsersDAO chatUsersDAO;

    @MockBean
    private ConversationsDAO conversationsDAO;

    @Test
    void sendMessageTest() {
        ChatUser chatUser = new ChatUser("testuser", new ArrayList<>());
        Optional<ChatUser> optionalChatUser = Optional.of(chatUser);
        Mockito.when(chatUsersDAO.findByUsername(chatUser.getUsername())).thenReturn(optionalChatUser);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("testuser", "contactuser", "testcontent", LocalDateTime.now()));
        Conversation conversation = new Conversation("test123", messages);
        Mockito.when(conversationsDAO.save(conversation)).thenReturn(conversation);

        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("test123","contactuser", false, false));
        ChatUser updatedChatUser = new ChatUser("testuser", chats);

        Mockito.when(chatUsersDAO.save(updatedChatUser)).thenReturn(updatedChatUser);

        assertThat(chatService.sendMessage(messages.get(0)).getStatus()).isEqualTo(ResponseStatus.OK);
    }

    @Test
    void getAllChatDetailsTest() {
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("test123","contact1", false, false));
        chats.add(new Chat("test456","contact2",false, true));
        ChatUser chatUser = new ChatUser("testuser", chats);
        Optional<ChatUser> optionalChatUser = Optional.of(chatUser);

        Mockito.when(chatUsersDAO.findByUsername(chatUser.getUsername())).thenReturn(optionalChatUser);

        assertThat(chatService.getAllChatDetails(chatUser.getUsername(), new ChatFilter())).isEqualTo(chatUser);
    }

    @Test
    void getConversationTest() {
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("test123","contact1", false, false));
        ChatUser chatUser = new ChatUser("testuser", chats);
        Optional<ChatUser> optionalChatUser = Optional.of(chatUser);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("testuser","contact1","testcontent", LocalDateTime.now()));
        Conversation conversation = new Conversation("test123", messages);
        Optional<Conversation> optionalConversation = Optional.of(conversation);

        Mockito.when(chatUsersDAO.findByUsername(chatUser.getUsername())).thenReturn(optionalChatUser);
        Mockito.when(chatUsersDAO.save(chatUser)).thenReturn(chatUser);
        Mockito.when(conversationsDAO.findConversationById("test123")).thenReturn(optionalConversation);

        assertThat(chatService.getConversation("testuser", "test123", new ChatFilter())).isEqualTo(conversation);

    }
}