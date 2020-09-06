package com.example.MessagingAPI.dao;

import com.example.MessagingAPI.model.Chat;
import com.example.MessagingAPI.model.ChatUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


@DataMongoTest
class ChatUsersDAOTest {


    @Autowired
    private ChatUsersDAO chatUsersDAO;

    @Test
    public void findByUsernameTest() {

        ArrayList<Chat> chats = new ArrayList<Chat>();
        chats.add(new Chat("1234", "testcontact", false, false));
        ChatUser chatUser = new ChatUser("test", chats);

        ChatUser chatUserSavedInDb = chatUsersDAO.save(chatUser);

        ChatUser chatUserFromInDb = chatUsersDAO.findByUsername(chatUserSavedInDb.getUsername()).get();
        assertEquals(chatUserFromInDb.getId(), chatUserSavedInDb.getId());
        assertEquals(chatUserFromInDb.getUsername(), chatUserSavedInDb.getUsername());
        assertEquals(chatUserFromInDb.getChats().size(), chatUserSavedInDb.getChats().size());

        chatUsersDAO.deleteById(chatUserSavedInDb.getId());
    }

    @Test
    public void findChatUsersByUsernameContainsTest() {
        ChatUser chatUser = new ChatUser("test", new ArrayList<>());

        ChatUser chatUserSavedInDb = chatUsersDAO.save(chatUser);

        List<ChatUser> chatUsersFromInDb = chatUsersDAO.findChatUsersByUsernameContains("te");
        assertTrue(chatUsersFromInDb.size()>0);

        chatUsersDAO.deleteById(chatUserSavedInDb.getId());
    }

}