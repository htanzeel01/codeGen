package io.swagger;

import io.swagger.model.Account;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserRepository;
import io.swagger.security.JwtTokenProvider;
import io.swagger.service.AccountService;
import io.swagger.service.UserImplementation;
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
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private RegistrationDTO registrationDTO;

    private User firstUser;
    private User secondUser;
    private Account firstAccount;
    private Account secondAccount;
    @Autowired
    private UserImplementation userService;
    @Autowired
    private AccountService accountService;

    List<User> userList = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registrationDTO = new RegistrationDTO("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        RegistrationDTO r1 = new RegistrationDTO("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUser = new User("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        secondUser =  new User("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUser.setUserId(1);
        firstUser.setPassword(passwordEncoder.encode(firstUser.getPassword()));
        secondUser.setUserId(2);
        secondUser.setPassword(passwordEncoder.encode(secondUser.getPassword()));
        userList.add(firstUser);
        userList.add(secondUser);
        userRepository.saveAll(userList);
        firstAccount = new Account(firstUser.getUsername(), new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        firstAccount.setUser(firstUser);
        firstAccount.setIban("NL55INHO0171");
        secondAccount = new Account(secondUser.getUsername(), new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        secondAccount.setUser(secondUser);
        accounts.add(firstAccount);
        accounts.add(secondAccount);
        accountService.save(firstAccount);
        accountService.save(secondAccount);
    }
}
