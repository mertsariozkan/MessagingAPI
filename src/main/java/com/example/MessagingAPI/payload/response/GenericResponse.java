package com.example.MessagingAPI.payload.response;

import com.example.MessagingAPI.payload.response.enums.ResponseStatus;

public class GenericResponse {
    private String message;
    private Enum<ResponseStatus> status;

    public GenericResponse(ResponseStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public GenericResponse(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Enum<ResponseStatus> getStatus() {
        return status;
    }

    public void setStatus(Enum<ResponseStatus> status) {
        this.status = status;
    }

}
