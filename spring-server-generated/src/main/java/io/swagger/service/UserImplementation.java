package io.swagger.service;

import io.swagger.exception.IncorrectUserTypeException;
import io.swagger.exception.LoginException;
import io.swagger.exception.RegistrationInvalidException;
import io.swagger.exception.UserNotFoundException;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserRepository;
import io.swagger.security.JwtTokenProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private UserRepository userRepository;

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
        if(checkValidMail(registrationDTO) && !user.getUsername().isEmpty()){
            registered = userRepository.save(user);
            return registered;
        }
        else{
            throw new RegistrationInvalidException("User can not be created");
        }
    }

    @Override
    public boolean checkValidMail(RegistrationDTO registrationDTO) {
        User userByEmail = userRepository.getUserByEmail(registrationDTO.getEmail());
        if(userByEmail==null){
            return true;
        }
        return false;
    }
    public String login(String username,String password) {
        try {
            if (password == null || password.length() == 0) {
                throw new LoginException(HttpStatus.UNPROCESSABLE_ENTITY, "Please enter a password.");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findUserByUsername(username);
            if (user == null) {
                throw new LoginException(HttpStatus.UNPROCESSABLE_ENTITY, "Could not find an user on this emailaddress.");
            }
            List<UserTypeEnum> roles = new ArrayList<>();
            roles.add(user.getUserType());
            return jwtTokenProvider.createToken(username, roles);
        } catch (AuthenticationException ex) {
            throw new LoginException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }

    }
    public List<User> getALLUsers(){
        return (List<User>) userRepository.findAll();
    }
    public User getAllUsersByUserName(String username){
        return (User) userRepository.findUserByUsername(username);
    }
    public User getUserByUserId(Integer userId) throws UserNotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user!=null){
        return user;
        }
        else {
            throw new UserNotFoundException("Users can not be found");
        }

    }
    @Override
    public void updateUser(Integer id, User user) throws UserNotFoundException, IncorrectUserTypeException {
        User u = userRepository.findUserByUserId(id);
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
        userRepository.save(u);
    }
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailAddress = authentication.getName();
        User loggedInUser = userRepository.findUserByUsername(emailAddress);
        if (loggedInUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication token was given.");
        }
        return loggedInUser;
    }
}
