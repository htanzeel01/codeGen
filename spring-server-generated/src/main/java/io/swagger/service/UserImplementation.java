package io.swagger.service;

import io.swagger.exception.RegistrationInvalidException;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class UserImplementation implements UserService {
    @Autowired
    private UserToCreateRepository userToCreateRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RegistrationDTO registrationDTO) throws RegistrationInvalidException {
        User user = new User(registrationDTO.getUserName(),registrationDTO.getPassword(),registrationDTO.getEmail(),registrationDTO.getFirstName(),registrationDTO.getLastName(),registrationDTO.getUsertype());
        User registered = null;
        if(checkMail(registrationDTO) && !user.getUsername().isEmpty()){
            registered = userToCreateRepository.save(user);
            return registered;
        }
        else{
            throw new RegistrationInvalidException("User can not be created");
        }

    }

    @Override
    public boolean checkMail(RegistrationDTO registrationDTO) {
        User userbyEmail =userToCreateRepository.getUserToCreateByEmail(registrationDTO.getEmail());
        if(userbyEmail==null){
            return true;
        }
        return false;
    }
    public String login(String username,String password) throws Exception {
        User user = userToCreateRepository.findUserToCreateByUsername(username);
        List<UserTypeEnum>enums=new ArrayList<>();
        enums.add(user.getUserType());
        if(user !=null){
        return jwtTokenProvider.createToken(username,enums);
        }else {
            throw new Exception("User name or password is wrong");
        }
    }
    public List<User> getALLUsers(){
        return (List<User>) userToCreateRepository.findAll();
    }
    public User getAllUsersByUserName(String username){
        return (User) userToCreateRepository.findUserToCreateByUsername(username);
    }
    public User getUserByUserId(Integer userId) throws Exception {
        User user = userToCreateRepository.findUserToCreateByUserId(userId);
        if(user!=null){
        return user;
        }
        else {
            throw new Exception("Users can not be found");
        }

    }
    @Override
    public void updateUser(Integer id, User user){
        User u = userToCreateRepository.findUserToCreateByUserId(id);
        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setUsername(user.getUsername());
        u.setLastName(user.getLastName());
        u.setUserType(user.getUserType());
        u.setPassword(user.getPassword());
        userToCreateRepository.save(u);
    }
}
