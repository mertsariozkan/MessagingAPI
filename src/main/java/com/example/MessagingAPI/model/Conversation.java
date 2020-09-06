package com.example.MessagingAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;
    private List<Message> messages;

    public Conversation() {
    }

    public Conversation(List<Message> messages) {
        this.messages = messages;
    }

    public Conversation(String id, List<Message> messages) {
        this.id = id;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
