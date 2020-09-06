package com.example.MessagingAPI.model;

import java.time.LocalDateTime;

public class Message {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;

    public Message() {
    }

    public Message(String from, String to, String content, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
