package com.example.MessagingAPI.payload.response;

import com.example.MessagingAPI.payload.response.enums.ResponseStatus;

import java.util.List;

public class SearchUserResponse extends GenericResponse {

    private List<String> users;

    public SearchUserResponse(ResponseStatus status, String message) {
        super(status, message);
    }

    public SearchUserResponse(ResponseStatus status, List<String> users) {
        super(status);
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
