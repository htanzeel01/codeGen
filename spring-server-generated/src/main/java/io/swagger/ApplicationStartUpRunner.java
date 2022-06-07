package io.swagger;

import io.swagger.model.Account;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import io.swagger.service.AccountService;
import io.swagger.service.UserToCreateImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    private UserToCreate firstUserToCreate;
    private UserToCreate secondUserToCreate;
    private Account firstAccount;
    private Account secondAccount;
    @Autowired
    private UserToCreateImpl userService;
    @Autowired
    private AccountService accountService;

    List<UserToCreate> userList = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registrationDTO = new RegistrationDTO("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        RegistrationDTO r1 = new RegistrationDTO("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUserToCreate = new UserToCreate("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        secondUserToCreate =  new UserToCreate("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUserToCreate.setUserId(1);
        secondUserToCreate.setUserId(2);
        userList.add(firstUserToCreate);
        userList.add(secondUserToCreate);
        userToCreateRepository.saveAll(userList);
        firstAccount = new Account(firstUserToCreate.getUsername(), new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        firstAccount.setUser(firstUserToCreate);
        secondAccount = new Account(secondUserToCreate.getUsername(), new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        secondAccount.setUser(secondUserToCreate);
        accounts.add(firstAccount);
        accounts.add(secondAccount);
        accountService.save(firstAccount);
        accountService.save(secondAccount);
    }
}
