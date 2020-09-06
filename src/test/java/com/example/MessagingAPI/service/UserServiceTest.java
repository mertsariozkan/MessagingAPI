package com.example.MessagingAPI.service;

import com.example.MessagingAPI.dao.ChatUsersDAO;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.Chat;
import com.example.MessagingAPI.model.ChatUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private ChatUsersDAO chatUsersDAO;


    @Test
    void blockUserTest() {
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("test123","user2", false, false));
        ChatUser chatUser = new ChatUser("user1", chats);
        Optional<ChatUser> optionalChatUser = Optional.of(chatUser);

        ArrayList<Chat> updatedChats = new ArrayList<>();
        chats.add(new Chat("test123","user2", false, true));
        ChatUser updatedChatUser = new ChatUser("user1", updatedChats);

        Mockito.when(chatUsersDAO.findByUsername(chatUser.getUsername())).thenReturn(optionalChatUser);
        Mockito.when(chatUsersDAO.save(updatedChatUser)).thenReturn(updatedChatUser);

    }

    @Test
    void unblockUserTest() {
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("test123","user2", false, true));
        ChatUser chatUser = new ChatUser("user1", chats);
        Optional<ChatUser> optionalChatUser = Optional.of(chatUser);

        ArrayList<Chat> updatedChats = new ArrayList<>();
        chats.add(new Chat("test123","user2", false, false));
        ChatUser updatedChatUser = new ChatUser("user1", updatedChats);

        Mockito.when(chatUsersDAO.findByUsername(chatUser.getUsername())).thenReturn(optionalChatUser);
        Mockito.when(chatUsersDAO.save(updatedChatUser)).thenReturn(updatedChatUser);

        assertThat(userService.unblockUser("user2", "user1").getStatus()).isEqualTo(ResponseStatus.OK);
    }

    @Test
    void searchUserTest() {
        ArrayList<ChatUser> users = new ArrayList<>();
        users.add(new ChatUser("user1", new ArrayList<>()));
        users.add(new ChatUser("user2", new ArrayList<>()));

        Mockito.when(chatUsersDAO.findChatUsersByUsernameContains("user")).thenReturn(users);

        assertThat(userService.searchUser("user").size()).isEqualTo(2);
    }

}