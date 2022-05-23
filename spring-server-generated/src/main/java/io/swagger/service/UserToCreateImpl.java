package io.swagger.service;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.Result;
import io.swagger.model.User;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Log
@Service
public class UserToCreateImpl implements UserToCreateService {
    @Autowired
    private UserToCreateRepository userToCreateRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserToCreate createUser(RegistrationDTO registrationDTO) throws Exception {
        UserToCreate userToCreate=new UserToCreate(registrationDTO.getUserName(),registrationDTO.getPassword(),registrationDTO.getEmail(),registrationDTO.getFirstName(),registrationDTO.getLastName(),registrationDTO.getUsertype());
        UserToCreate registered=null;
        if(cheackMail(registrationDTO)){
            registered=userToCreateRepository.save(userToCreate);
            return registered;
        }
        else{
            throw new Exception("User can not be created");
        }

    }

    @Override
    public boolean cheackMail(RegistrationDTO registrationDTO) {
        UserToCreate emailChecker =userToCreateRepository.getUserToCreateByEmail(registrationDTO.getEmail());
        if(emailChecker==null){
            return true;
        }
        return false;
    }
    public String login(String username,String password) throws Exception {
        UserToCreate userToCreate = userToCreateRepository.findUserToCreateByUsername(username);
        List<UserTypeEnum>enums=new ArrayList<>();
        enums.add(userToCreate.getUserType());
        if(userToCreate!=null){
        return jwtTokenProvider.createToken(username,enums);
        }else {
            throw new Exception("User name or password is wrong");
        }
    }
    public List<UserToCreate> getALLUsers(){
        return (List<UserToCreate>) userToCreateRepository.findAll();
    }
    public UserToCreate getAllUsersByUserName(String username){
        return (UserToCreate) userToCreateRepository.findUserToCreateByUsername(username);
    }
    public UserToCreate getUserByUserId(Integer userId) throws Exception {
        UserToCreate user = userToCreateRepository.findUserToCreateByUserId(userId);
        if(user!=null){
        return user;
        }
        else {
            throw new Exception("Users can not be found");
        }

    }
    @Override
    public void updateUser(Integer id,UserToCreate user){
        UserToCreate u = userToCreateRepository.findUserToCreateByUserId(id);
        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setUsername(user.getUsername());
        u.setLastName(user.getLastName());
        u.setUserType(user.getUserType());
        u.setPassword(user.getPassword());
        userToCreateRepository.save(u);
        //userToCreateRepository.updateUser(u.getUserType(),u.getUsername(),u.getFirstName(),u.getLastName(),u.getEmail(),u.getPassword(),id);*/

    }
}
