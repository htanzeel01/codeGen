package io.swagger.service;

import io.swagger.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {
    List<Account> getAllByUser(Integer userId) throws Exception;
    void save(Account account) throws Exception;
    List<Account> GetAccountbyName(String name);
    String ibanFormat();
    boolean ibanCheck(String iban);
    Account getbyIban(String iban);
    int deposit(String iban, int amount) throws Exception;
    Account withdraw(String iban,int amount) throws Exception;
    void closeAccount(String iban);
}
