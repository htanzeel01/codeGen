package io.swagger.api;

import io.swagger.model.TransactionResult;
import io.swagger.model.Transactions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.TransactionService;
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

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Transactions> getTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID) {

        try {
            Transactions transactions = transactionService.getTransactionsById(ID);
            return new ResponseEntity<Transactions>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Transactions>(HttpStatus.BAD_REQUEST);

        }
    }
    /*public ResponseEntity<List<Transactions>> getTransactions(@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "Enter the users ID" ,schema=@Schema()) @Valid @RequestParam(value = "UserID", required = false) String userID,@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "Start date", required = false) String startDate,@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "End date", required = false) String endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transactions>>(objectMapper.readValue("[ {\n  \"amount\" : 25.8,\n  \"userPerforming\" : \"54N45GHS\",\n  \"from\" : \"IBAN5555\",\n  \"id\" : 12345,\n  \"to\" : \"IBAN6666\",\n  \"transactionDate\" : \"15-05-2021\"\n}, {\n  \"amount\" : 25.8,\n  \"userPerforming\" : \"54N45GHS\",\n  \"from\" : \"IBAN5555\",\n  \"id\" : 12345,\n  \"to\" : \"IBAN6666\",\n  \"transactionDate\" : \"15-05-2021\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transactions>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transactions>>(HttpStatus.NOT_IMPLEMENTED);
    }*/
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Transactions>> getTransactionsFromAccountId(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "iban", required = true) String iban) {

        try {
            if (iban == null){
                throw new Exception("iban is null");
            }
            List<Transactions> transactions =transactionService.getAllTransactions(iban);
            return new ResponseEntity<List<Transactions>>(transactions, HttpStatus.OK);

        }
          catch (Exception e) {
              return new ResponseEntity<List<Transactions>>(HttpStatus.BAD_GATEWAY);

          }
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<TransactionResult> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Transactions body) throws Exception {
        try {
            body.setTransactionDate(LocalDateTime.now());
            body.setUserperforming(body.getAccountfrom().getUser().getUserType());
            transactionService.createTransaction(body);
            TransactionResult transactionResult = new TransactionResult();
            transactionResult.setMessage("You finaly made it!");
            transactionResult.setSuccess("Successfully transferred the amount");
            return new ResponseEntity<TransactionResult>(transactionResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<TransactionResult>(HttpStatus.BAD_REQUEST);
        }
    }
}
