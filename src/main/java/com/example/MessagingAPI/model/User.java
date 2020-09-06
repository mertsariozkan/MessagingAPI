package com.example.MessagingAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "INVALID FIELD: Username can not be blank.")
    @Size(min = 3, message = "INVALID FIELD: Minimum length for username is 6 characters.")
    private String username;

    @NotBlank(message = "INVALID FIELD: Password can not be blank.")
    @Size(min = 6, message = "INVALID FIELD: Minimum length for username is 6 characters.")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
