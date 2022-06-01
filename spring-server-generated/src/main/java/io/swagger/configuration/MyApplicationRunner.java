package io.swagger.configuration;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.service.UserToCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    UserToCreateService userToCreateService;

    @Autowired
    UserToCreateRepository userToCreateRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
    private UserToCreate initCustomerUser(RegistrationDTO registrationDTO) throws Exception {
        registrationDTO  = new RegistrationDTO();
        registrationDTO.setEmail("mahedi@gmail.com");
        registrationDTO.setFirstName("Mahedi");
        registrationDTO.setLastName("Mridul");
        registrationDTO.setUsertype(UserTypeEnum.ROLE_CUSTOMER);

        registrationDTO.setUserName("Mahedi");
        registrationDTO.setPassword("Mahedi1243");
        UserToCreate userToCreate= userToCreateService.createUser(registrationDTO);
        return userToCreate;
    }
}
