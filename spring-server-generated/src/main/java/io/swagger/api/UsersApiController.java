package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private UserService userService;
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
    public ResponseEntity<User> getUserByID(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("userId") Integer userId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailAddress = authentication.getName();

//            User loggedInUser = userService.getUserByEmailAddress(emailAddress);
//            if (loggedInUser == null) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication token was given.");
//            }
//            if (!loggedInUser.getRole().contains(UserRole.EMPLOYEE) && loggedInUser.getUserId() != userId) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The current auth token does not provide access to this resource.");
//            }
            User user = userService.getUserByUserId(userId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "find user by userName" ,schema=@Schema()) @Valid @RequestParam(value = "userName", required = false) String userName, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
, defaultValue="50")) @Valid @RequestParam(value = "limit", required = false, defaultValue="50") Integer limit) {
        if(userName==null){
            return new ResponseEntity<List<User>>(userService.getALLUsers(),HttpStatus.OK);
        }
        else {
            User user =  userService.getAllUsersByUserName(userName);
            List<User> users = new ArrayList<>();
            users.add(user);
            return new ResponseEntity<List<User>>(users,HttpStatus.OK);
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> updateUserById(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId,@Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required=true, schema=@Schema()) @Valid @RequestBody User body) {
        try {
            userService.updateUser(userId,body);
            return new ResponseEntity<Void>(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
