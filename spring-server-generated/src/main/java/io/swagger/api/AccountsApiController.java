package io.swagger.api;

import io.swagger.exception.IncorrectAccountException;
import io.swagger.exception.UnAuthorizedException;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<DepositResult> accountDeposit(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Deposit body) {
        Account accountChecker = accountService.getbyIban(iban);
        try {
            if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE || userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_CUSTOMER) {
                if (userService.getLoggedInUser().getUserId() == accountChecker.getUser().getUserId()) {
                    accountService.deposit(iban, body.getDepositAmount());
                    DepositResult result = new DepositResult();
                    result.setSuccess("Balance deposited successfuly");
                    result.setTime(LocalDateTime.now());
                    //result.setCurrentBalance(amount);
                    return new ResponseEntity<DepositResult>(result, HttpStatus.OK);
                }
                else{
                    throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to deposit into this account");
                }
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to deposit into this account");
            }
        }
        catch (Exception e){
            return ResponseEntity.status((HttpStatus.BAD_GATEWAY)).build();
        }
    }
    public ResponseEntity<Void> accountID(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") Integer IBAN) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Withdrawresult> accountWithdrawl(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Withdraw body) {
            Account accountChecker = accountService.getbyIban(iban);
        try {
            if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE || userService.getLoggedInUser().getUserType()==UserTypeEnum.ROLE_CUSTOMER) {
                if (userService.getLoggedInUser().getUserId()==accountChecker.getUser().getUserId()) {
                    Account account = accountService.withdraw(iban, body.getWithdrawAmount());
                    Withdrawresult withdrawresult = new Withdrawresult();
                    //withdrawresult.setRemainingbalance(account.getBalance());
                    withdrawresult.setSuccess("withdraw success");
                    //withdrawresult.setDailyLimit(700);
                    withdrawresult.setTime(LocalDateTime.now());
                    return new ResponseEntity<>(withdrawresult, HttpStatus.ACCEPTED);
                }
                else {
                    throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to withdraw from this account");
                }
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to withdraw from this account");
            }
        }
        catch (Exception e){
            return ResponseEntity.status((HttpStatus.BAD_GATEWAY)).build();
        }
    }

    public ResponseEntity<Account> getAccounts(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {

        if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE) {
            Account account = accountService.getbyIban(IBAN);
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        } else {
            throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to close account");
        }
    }
    public ResponseEntity<Void> closeAccount(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        try{
            if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE){
                accountService.closeAccount(IBAN);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to close account");
            }

        }catch(Exception exception){

            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
