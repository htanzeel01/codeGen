package io.swagger.service;

import io.swagger.exception.IncorrectUserTypeException;
import io.swagger.exception.RegistrationInvalidException;
import io.swagger.exception.UserNotFoundException;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private PasswordEncoder passwordEncoder ;

    @Override
    public User createUser(RegistrationDTO registrationDTO) throws RegistrationInvalidException {
        User user = new User(registrationDTO.getUserName(),registrationDTO.getPassword(),
                registrationDTO.getEmail(),registrationDTO.getFirstName(),registrationDTO.getLastName(),
                registrationDTO.getUsertype());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        User userByEmail =userToCreateRepository.getUserByEmail(registrationDTO.getEmail());
        if(userByEmail==null){
            return true;
        }
        return false;
    }
    public String login(String username,String password) throws Exception {
        User user = userToCreateRepository.findUserByUsername(username);
        List<UserTypeEnum>enums=new ArrayList<>();
        enums.add(user.getUserType());
        if(user != null){
        return jwtTokenProvider.createToken(username,enums);
        }else {
            throw new Exception("User name or password is wrong");
        }
    }
    public List<User> getALLUsers(){
        return (List<User>) userToCreateRepository.findAll();
    }
    public User getAllUsersByUserName(String username){
        return (User) userToCreateRepository.findUserByUsername(username);
    }
    public User getUserByUserId(Integer userId) throws UserNotFoundException {
        User user = userToCreateRepository.findUserByUserId(userId);
        if(user!=null){
        return user;
        }
        else {
            throw new UserNotFoundException("Users can not be found");
        }

    }
    @Override
    public void updateUser(Integer id, User user) throws UserNotFoundException, IncorrectUserTypeException {
        User u = userToCreateRepository.findUserByUserId(id);
        if (u == null) {
            throw new UserNotFoundException(" not find an user with the given user ID.");
        }
        if (user.getEmail() != null) u.setEmail(user.getEmail());
        if (user.getFirstName() != null) u.setFirstName(user.getFirstName());
        if (user.getUsername() != null) u.setUsername(user.getUsername());
        if (user.getLastName() != null) u.setLastName(user.getLastName());
        if (user.getUserType() != UserTypeEnum.ROLE_CUSTOMER && user.getUserType() !=UserTypeEnum.ROLE_EMPLOYEE)
        {
            throw new IncorrectUserTypeException(" Your enter user Role is not Valid");
        }
        if (user.getUserType() != null) u.setUserType(user.getUserType());
        if (user.getPassword() != null) u.setPassword(passwordEncoder.encode(user.getPassword()));
        userToCreateRepository.save(u);
    }
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailAddress = authentication.getName();
        User loggedInUser = userToCreateRepository.findUserByUsername(emailAddress);
        if (loggedInUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication token was given.");
        }
        return loggedInUser;
    }
}
