package io.swagger.api;

import io.swagger.exception.LoginException;
import io.swagger.model.DTO.JwtToken;
import io.swagger.model.DTO.UserLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.UserImplementation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private UserImplementation userToCreate;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JwtToken> getlogin(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserLogin body) throws Exception {
        JwtToken jwtToken = new JwtToken();
        if(body.getUsername() != null && body.getPassword()!= null){
        String key = userToCreate.login(body.getUsername(), body.getPassword());
        jwtToken.setKey(key);
        }
        else {
            throw new LoginException(HttpStatus.UNPROCESSABLE_ENTITY, "Username or password cannot be empty");
        }
        return new ResponseEntity<JwtToken>(jwtToken, HttpStatus.OK);
    }

}
