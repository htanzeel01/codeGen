package io.swagger.configuration;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    UserService userService;

    @Autowired
    UserToCreateRepository userToCreateRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
    private User initCustomerUser(RegistrationDTO registrationDTO) throws Exception {
        registrationDTO  = new RegistrationDTO();
        registrationDTO.setEmail("mahedi@gmail.com");
        registrationDTO.setFirstName("Mahedi");
        registrationDTO.setLastName("Mridul");
        registrationDTO.setUsertype(UserTypeEnum.ROLE_CUSTOMER);

        registrationDTO.setUserName("Mahedi");
        registrationDTO.setPassword("Mahedi1243");
        User user = userService.createUser(registrationDTO);
        return user;
    }
}
