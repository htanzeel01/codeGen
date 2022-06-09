package io.swagger.api;

import io.swagger.exception.RegistrationInvalidException;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.UserToCreateService;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class RegisterApiController implements RegisterApi {

    private static final Logger log = LoggerFactory.getLogger(RegisterApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private UserToCreateService userToCreateService;

    @org.springframework.beans.factory.annotation.Autowired
    public RegisterApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Result> register(@NotNull @Parameter(in = ParameterIn.QUERY, description = "To give the user either the role Customer or Employee" ,required=true,schema=@Schema(allowableValues={ "Employee", "Customer" }
)) @Valid @RequestParam(value = "userType", required = true) String userType,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody RegistrationDTO registrationDTO) throws Exception {

        Result result = new Result();
        try {
            result.setMessage("User has been registered");
            result.setSuccess(true);
            userToCreateService.createUser(registrationDTO);
            return new ResponseEntity<Result>(result,HttpStatus.CREATED);
        } catch (RegistrationInvalidException ex){
            result.setMessage(ex.getMessage());
            result.setSuccess(false);
            return new ResponseEntity<Result>(result,HttpStatus.BAD_REQUEST);
        }
    }
}
