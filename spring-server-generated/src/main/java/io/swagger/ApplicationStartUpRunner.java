package io.swagger;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import io.swagger.service.UserToCreateImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
@Transactional
public class ApplicationStartUpRunner implements ApplicationRunner {
    @Autowired
    private UserToCreateRepository userToCreateRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private RegistrationDTO registrationDTO;

    private UserToCreate userToCreate;
    @Autowired
    private UserToCreateImpl userService;

    List<UserToCreate> userList = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registrationDTO = new RegistrationDTO("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        RegistrationDTO r1= new RegistrationDTO("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        userList.add(new UserToCreate("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE));
        userList.add(new UserToCreate("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER));
        userToCreateRepository.saveAll(userList);
    }
}
