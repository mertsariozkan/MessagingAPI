package com.example.MessagingAPI.payload.request;

public class BlockUserRequest {
    private String blockingUser;
    private String blockedUser;

    public String getBlockingUser() {
        return blockingUser;
    }

    public void setBlockingUser(String blockingUser) {
        this.blockingUser = blockingUser;
    }

    public String getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(String blockedUser) {
        this.blockedUser = blockedUser;
    }
}
