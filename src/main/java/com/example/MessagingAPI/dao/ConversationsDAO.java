package com.example.MessagingAPI.dao;

import com.example.MessagingAPI.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConversationsDAO extends MongoRepository<Conversation, String> {

    Optional<Conversation> findConversationById(String id);

}
