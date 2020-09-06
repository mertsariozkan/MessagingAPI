package com.example.MessagingAPI.controller;

import com.example.MessagingAPI.payload.request.LoginRequest;
import com.example.MessagingAPI.payload.request.SignupRequest;
import com.example.MessagingAPI.payload.response.GenericResponse;
import com.example.MessagingAPI.payload.response.JwtResponse;
import com.example.MessagingAPI.dao.UserDAO;
import com.example.MessagingAPI.payload.response.enums.ResponseStatus;
import com.example.MessagingAPI.model.User;
import com.example.MessagingAPI.model.UserDetailsImpl;
import com.example.MessagingAPI.security.JwtUtils;
import com.example.MessagingAPI.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            String userName = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            logger.info("SUCCESSFUL LOGIN: '" + user.getUsername() + "' logged in successfully.");
            return ResponseEntity.ok(new JwtResponse(jwt,
                    user.getId(),
                    user.getUsername()));
        } catch (Exception e) {
            logger.error("INVALID LOGIN: '" + loginRequest.getUsername() + "' couldn't login.");
            return ResponseEntity.ok(new GenericResponse(ResponseStatus.ERROR, "Invalid login"));
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
            logger.error("INVALID SIGNUP: '" + signUpRequest.getUsername() + "' username is already taken.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse(ResponseStatus.ERROR,"Error: Username is already taken."));
        }

        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        userDao.save(user);
        userService.createChatUser(user.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            logger.info("SUCCESSFUL SIGNUP: '" + user.getUsername() + "' registered successfully.");
            return ResponseEntity.ok(new JwtResponse(jwt,
                    user.getId(),
                    user.getUsername()));

        } catch (Exception e) {
            logger.error("INVALID SIGNUP: '" + signUpRequest.getUsername() + "' couldn't register.");
            return ResponseEntity.ok(new GenericResponse(ResponseStatus.ERROR, "Invalid signup"));
        }
    }
}
