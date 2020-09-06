package com.example.MessagingAPI.controller;

import com.example.MessagingAPI.payload.request.BlockUserRequest;
import com.example.MessagingAPI.payload.request.SearchUserRequest;
import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.payload.response.SearchUserResponse;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public SearchUserResponse searchUser(@Valid @RequestBody SearchUserRequest request) {
        List<String> usernameList = userService.searchUser(request.getUsername());
        if(usernameList == null)
            usernameList = new ArrayList<>();
        return new SearchUserResponse(ResponseStatus.OK, usernameList);
    }

    @PostMapping("/block")
    public GenericResponse blockUser(@Valid @RequestBody BlockUserRequest request) {
        return userService.blockUser(request.getBlockingUser(), request.getBlockedUser());
    }

    @PostMapping("/unblock")
    public GenericResponse unblockUser(@Valid @RequestBody BlockUserRequest request) {
        return userService.unblockUser(request.getBlockingUser(), request.getBlockedUser());
    }

}
