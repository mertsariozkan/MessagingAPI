package com.example.MessagingAPI.model;

public class ChatFilter {
    private String usernameFilter;
    private int page;

    public ChatFilter() {
        this.page = 1;
    }

    public ChatFilter(int page) {
        this.page = page;
    }

    public ChatFilter(String usernameFilter) {
        this.usernameFilter = usernameFilter;
        this.page = 1;
    }

    public ChatFilter(String usernameFilter, int page) {
        this.usernameFilter = usernameFilter;
        this.page = page;
    }

    public String getUsernameFilter() {
        return usernameFilter;
    }

    public void setUsernameFilter(String usernameFilter) {
        this.usernameFilter = usernameFilter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
