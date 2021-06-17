package io.swagger.model.DTO;

import io.swagger.model.UserTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegistrationDTO {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserTypeEnum usertype;
    public RegistrationDTO(){

    }
    public RegistrationDTO(String userName, String password, String email, String firstName, String lastName, UserTypeEnum usertype) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.usertype = usertype;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!email.contains("@") || !email.contains(".")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Not a valid email address");
        }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserTypeEnum getUsertype() {
        return usertype;
    }

    public void setUsertype(UserTypeEnum usertype) {
        this.usertype = usertype;
    }





}
