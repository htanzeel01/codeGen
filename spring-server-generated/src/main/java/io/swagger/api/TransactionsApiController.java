package io.swagger.api;

import io.swagger.exception.UnAuthorizedException;
import io.swagger.model.Account;
import io.swagger.model.TransactionResult;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.extensions.compactnotation.PackageCompactConstructor;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    public ResponseEntity<Transaction> getTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID) {

        try {
            if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE) {
                Transaction transaction = transactionService.getTransactionsById(ID);
                return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to get this transaction");
            }
        } catch (Exception e) {
            return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<List<Transaction>> getTransactionsFromAccountId(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "iban", required = true) String iban) {

        try {
            if (iban == null){
                throw new Exception("iban is null");
            }
           else if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE){
                List<Transaction> transactions =transactionService.getAllTransactions(iban);
                return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to get this transaction");
            }
        }
          catch (Exception e) {
              return new ResponseEntity<List<Transaction>>(HttpStatus.BAD_GATEWAY);

          }
    }


    public ResponseEntity<TransactionResult> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Transaction body) throws Exception {

        try {
            Account accountChecker = accountService.getbyIban(body.getAccountfrom().getIban());
            if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE) {
                body.setTransactionDate(LocalDateTime.now());
                body.setUserperforming(body.getAccountfrom().getUser().getUserType());
                body.setDayLimit();
                transactionService.createTransaction(body);
                TransactionResult transactionResult = new TransactionResult();
                transactionResult.setMessage("You finaly made it!");
                transactionResult.setSuccess("Successfully transferred the amount");
                return new ResponseEntity<TransactionResult>(transactionResult, HttpStatus.OK);
            }
            else if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_CUSTOMER){
                if (userService.getLoggedInUser().getUserId() == accountChecker.getUser().getUserId()){
                    body.setTransactionDate(LocalDateTime.now());
                    body.setUserperforming(body.getAccountfrom().getUser().getUserType());
                    body.setDayLimit();
                    transactionService.createTransaction(body);
                    TransactionResult transactionResult = new TransactionResult();
                    transactionResult.setMessage("You finaly made it!");
                    transactionResult.setSuccess("Successfully transferred the amount");
                    return new ResponseEntity<TransactionResult>(transactionResult, HttpStatus.OK);
                }
                else {
                    throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to make this transaction");
                }
            }
            else {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, "You are not authorized to make this transaction");
            }
        }
            catch (Exception e) {
                throw new UnAuthorizedException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Transaction>> getCertainDateOfTransactions( @Valid String startDate,  @Valid String endDate) throws Exception {
        if (userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_EMPLOYEE || userService.getLoggedInUser().getUserType() == UserTypeEnum.ROLE_CUSTOMER){
            List<Transaction> transactionList = transactionService.getAllTransactionsBetween2Months(startDate,endDate, userService.getLoggedInUser());
            return new ResponseEntity<List<Transaction>>(transactionList,HttpStatus.OK);
        }
        else {
            throw new UnAuthorizedException(HttpStatus.FORBIDDEN,"Incorrect details");
        }
    }


}
