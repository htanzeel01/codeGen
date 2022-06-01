package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.UserToCreateImpl;
import io.swagger.service.UserToCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private UserToCreateService userToCreateService;
    @Autowired
    private AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Account>> getUserAccount(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("userId") Integer userId) {
        try {
            return new ResponseEntity<List<Account>>(accountService.getAllByUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserToCreate> getUserByID(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("userId") Integer userId) {

        try {
                UserToCreate user = userToCreateService.getUserByUserId(userId);
                return new ResponseEntity<UserToCreate>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<UserToCreate>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserToCreate>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "find user by userName" ,schema=@Schema()) @Valid @RequestParam(value = "userName", required = false) String userName, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
, defaultValue="50")) @Valid @RequestParam(value = "limit", required = false, defaultValue="50") Integer limit) {
        if(userName==null){
            return new ResponseEntity<List<UserToCreate>>(userToCreateService.getALLUsers(),HttpStatus.OK);
        }
        else {
            UserToCreate user =  userToCreateService.getAllUsersByUserName(userName);
            List<UserToCreate>userToCreates = new ArrayList<>();
            userToCreates.add(user);
            return new ResponseEntity<List<UserToCreate>>(userToCreates,HttpStatus.OK);
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> updateUserById(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId,@Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required=true, schema=@Schema()) @Valid @RequestBody UserToCreate body) {
        try {
            userToCreateService.updateUser(userId,body);
            return new ResponseEntity<Void>(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
