package com.example.MessagingAPI.dao;

import com.example.MessagingAPI.model.ChatUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatUsersDAO extends MongoRepository<ChatUser, String> {
    Optional<ChatUser> findByUsername(String username);
    List<ChatUser> findChatUsersByUsernameContains(String username);
}
