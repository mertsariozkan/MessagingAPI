package com.example.MessagingAPI.dao;

import com.example.MessagingAPI.model.Conversation;
import com.example.MessagingAPI.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class ConversationsDAOTest {

    @Autowired
    private ConversationsDAO conversationsDAO;

    @Test
    public void findConversationByIdTest() {
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new Message("test", "testcontact", "testcontent", LocalDateTime.now()));
        Conversation conversation = new Conversation("1234test",messages);

        Conversation conversationSavedInDb = conversationsDAO.save(conversation);

        Conversation conversationFromInDb = conversationsDAO.findConversationById("1234test").get();
        assertEquals(conversationFromInDb.getId(), conversationSavedInDb.getId());
        assertEquals(conversationFromInDb.getMessages().size(), conversationSavedInDb.getMessages().size());

        conversationsDAO.deleteById(conversationFromInDb.getId());
    }

}