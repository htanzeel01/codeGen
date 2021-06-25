package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.User;
import io.swagger.model.UserToCreate;
import io.swagger.service.TransactionService;
import io.swagger.service.UserToCreateService;
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
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserToCreateService userToCreateService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Transactions> getTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID) {
        try {
            Transactions transaction = transactionService.getTransactionByID(ID);
            return new ResponseEntity<Transactions>(transaction,HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<Transactions>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Transactions>> getTransactions(@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "Enter the users ID" ,schema=@Schema()) @Valid @RequestParam(value = "UserID", required = false) String userID,@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "Start date", required = false) String startDate,@DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "End date", required = false) String endDate) {


        return new ResponseEntity<List<Transactions>>(HttpStatus.BAD_REQUEST);

    }

}
