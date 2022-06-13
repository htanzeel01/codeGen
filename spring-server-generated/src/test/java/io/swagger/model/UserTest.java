package io.swagger.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user ;


    @BeforeEach
    public void setup(){
        user = new User();
    }
    @Test
    public void UserToCreateCanNotBeNull(){
        assertNotNull(user);
    }


    @Test
    public void getUserId() {
    }
}