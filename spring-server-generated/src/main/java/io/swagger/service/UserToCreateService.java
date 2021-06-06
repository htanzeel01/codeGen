package io.swagger.service;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.Result;
import io.swagger.model.UserToCreate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserToCreateService {
    UserToCreate createUser(RegistrationDTO registrationDTO) throws Exception;
    boolean cheackMail(RegistrationDTO registrationDTO);
    String login(String username,String password) throws Exception;
    List<UserToCreate> getALLUsers();
    UserToCreate getAllUsersByUserName(String username);
    UserToCreate getUserByUserId(Integer userId) throws Exception;
}
