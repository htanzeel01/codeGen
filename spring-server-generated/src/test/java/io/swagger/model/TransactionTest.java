package io.swagger.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Transactions transactions;
    private Account account;
    private UserToCreate userToCreate;



    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.ROLE_CUSTOMER);
        account = new Account("Mahedi",new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55435435435435");
        account.setUser(userToCreate);

        transactions = new Transactions();
        transactions.setAccountfrom(account);
        transactions.setAccountto(transactions.getAccountto());
        transactions.setAmount(new BigDecimal(20.00));
        transactions.setTransactionDate(LocalDateTime.now());
        transactions.setUserperforming(Account.AccountTypeEnum.CURRENT);
    }

    @Test
    public void createTransactionShouldNotBeNull() {
        assertNotNull(transactions);
    }

    @Test
    public void setNegativeAmountShouldThrowError() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transactions.setAmount(new BigDecimal(-10)));
        assertEquals("Invalid amount.", exception.getMessage());
    }

}
