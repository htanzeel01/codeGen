package io.swagger.Model;

import io.swagger.model.UserToCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserToCreateTest {
    private UserToCreate user ;


    @BeforeEach
    public void setup(){
        user = new UserToCreate();
    }
    @Test
    public void UserToCreateCanNotBeNull(){
        assertNotNull(user);
    }


    @Test
    public void getUserId() {
    }
}