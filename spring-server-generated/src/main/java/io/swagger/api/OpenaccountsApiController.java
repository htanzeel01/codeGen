package io.swagger.api;

import io.swagger.exception.IncorrectAccountException;
import io.swagger.model.Account;
import io.swagger.model.AccountResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class OpenaccountsApiController implements OpenaccountsApi {

    private static final Logger log = LoggerFactory.getLogger(OpenaccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public OpenaccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AccountResult> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Account account) throws Exception {
        AccountResult result = new AccountResult();
        try {
            accountService.save(account);
            result.setMessage("Account Created");
            result.setIBAN(account.getIban());
            return new ResponseEntity<AccountResult>(result, HttpStatus.OK);
        }
        catch (IncorrectAccountException e){
            result.setMessage(e.getMessage());
            return new ResponseEntity<AccountResult>(result, HttpStatus.BAD_GATEWAY);
        }
    }

}
