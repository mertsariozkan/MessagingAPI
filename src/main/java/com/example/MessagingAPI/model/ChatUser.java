package com.example.MessagingAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "chat_users")
public class ChatUser {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private ArrayList<Chat> chats;

    public ChatUser() { }

    public ChatUser(String username, ArrayList<Chat> chats) {
        this.username = username;
        this.chats = chats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
}
