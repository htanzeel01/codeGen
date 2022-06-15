package io.swagger.service;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(RegistrationDTO registrationDTO) throws Exception;
    boolean checkMail(RegistrationDTO registrationDTO);
    String login(String username,String password) throws Exception;
    List<User> getALLUsers();
    User getAllUsersByUserName(String username);
    User getUserByUserId(Integer userId) throws Exception;
    void updateUser(Integer id, User user);
}
