package com.example.MessagingAPI.dao;

import com.example.MessagingAPI.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void findByUsernameTest() {

        User user = new User("test", "test123");

        User userSavedInDb = userDAO.save(user);

        User userFromInDb = userDAO.findByUsername("test").get();
        assertEquals(userFromInDb.getUsername(), userSavedInDb.getUsername());

        userDAO.deleteById(userFromInDb.getId());
    }

    public void existsByUsernameTest() {
        User user = new User("test", "test123");

        User userSavedInDb = userDAO.save(user);

        Boolean userExists = userDAO.existsByUsername("test");
        assertTrue(userExists);

        userDAO.deleteById(userSavedInDb.getId());
    }

}