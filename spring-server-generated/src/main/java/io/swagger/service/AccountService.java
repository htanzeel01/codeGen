package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public interface AccountService {
    List<Account> getAllUserByUserId(Integer userId);
    void save(Account account) throws Exception;
    String ibanFormat();
    boolean ibanCheck(String iban);
    Account getbyIban(String iban);
    int deposit(String iban, int amount) throws Exception;
    Account withdraw(String iban,int amount) throws Exception;
    void closeAccount(String iban);
    int updateAmount(String iban, BigDecimal amount) throws Exception;
    int increaseAmount(String iban,BigDecimal amount);
    boolean accountCheck(String iban1, String iban2);
    Account getAccountbyUserId(User user);
}
