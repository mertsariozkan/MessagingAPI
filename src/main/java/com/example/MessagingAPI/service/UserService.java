package com.example.MessagingAPI.service;

import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.dao.ChatUsersDAO;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.Chat;
import com.example.MessagingAPI.model.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private ChatUsersDAO chatUsersDAO;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    public void createNewMessageNotification(String chatId, String username) {
        Optional<ChatUser> optionalChatUser = chatUsersDAO.findByUsername(username);
        if(optionalChatUser.isPresent()) {
            ChatUser chatUser = optionalChatUser.get();
            ArrayList<Chat> chats = chatUser.getChats();
            for(Chat c : chats) {
                if(c.getChatId().equals(chatId)) {
                    c.setHasNewMessage(true);
                }
            }
            chatUser.setChats(chats);
            chatUsersDAO.save(chatUser);
        }
    }

    @Override
    public GenericResponse blockUser(String blockingUser, String blockedUser) {
        return updateIsBlockedField(true, blockingUser, blockedUser);
    }

    @Override
    public GenericResponse unblockUser(String unblockingUser, String unblockedUser) {
        return updateIsBlockedField(false, unblockingUser, unblockedUser);
    }

    @Override
    public List<String> searchUser(String nickname) {
        List<ChatUser> users = chatUsersDAO.findChatUsersByUsernameContains(nickname);
        List<String> usernameList = new ArrayList<>();
        for(ChatUser chatUser : users) {
            usernameList.add(chatUser.getUsername());
        }
        logger.info("SEARCH OPERATION: '"+nickname+"' has searched.");
        return usernameList;
    }

    @Override
    public ChatUser createChatUser(String username) {
        ChatUser chatUser = new ChatUser(username, new ArrayList<>());
        return chatUsersDAO.save(chatUser);
    }

    private GenericResponse updateIsBlockedField(boolean isBlocking, String updatingUser, String updatedUser) {
        Optional<ChatUser> optionalChatUser = chatUsersDAO.findByUsername(updatedUser);
        if(!optionalChatUser.isPresent())
            return new GenericResponse(ResponseStatus.ERROR, "User could not be found.");

        ChatUser chatUser = optionalChatUser.get();
        ArrayList<Chat> chats = chatUser.getChats();
        for(Chat c : chats) {
            if(c.getContact().equals(updatingUser)) {
                c.setBlocked(isBlocking);
            }
        }
        chatUser.setChats(chats);
        chatUsersDAO.save(chatUser);

        if(isBlocking) {
            logger.info("BLOCKING OPERATION: " +updatingUser+ " has blocked " +updatedUser+".");
            return new GenericResponse(ResponseStatus.OK, "User has blocked");
        } else {
            logger.info("UNBLOCKING OPERATION: " +updatingUser+ " has unblocked " +updatedUser+".");
            return new GenericResponse(ResponseStatus.OK, "User has unblocked");
        }
    }


}
