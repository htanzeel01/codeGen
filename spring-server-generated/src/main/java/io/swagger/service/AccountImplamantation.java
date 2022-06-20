package io.swagger.service;

import io.swagger.exception.AccountNotFoundException;
import io.swagger.exception.IncorrectAccountException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountImplamantation implements AccountService{

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserService userService;

    @Override
    public void save(Account account) throws IncorrectAccountException {
        String setiban = ibanFormat();
        account.setIban(setiban);
        User user = userService.getAllUsersByUserName(account.getUser().getUsername());
        account.setUser(user);
        if (account.getUser() != null) {
            accountRepository.save(account);
        }
        else {
            throw new IncorrectAccountException("incrorrect user");
        }
    }


    @Override
    public String ibanFormat() {
        Random rnd = new Random();
        // String iban = String.format("NL",rnd.nextInt(100),"INHO%07d",rnd.nextInt(1000));
        String iban = "NL" + rnd.nextInt(100) + "INHO" + ThreadLocalRandom.current().nextInt();
        while (ibanCheck(iban) == false){
            iban = "NL" + rnd.nextInt(100) + "INHO" + ThreadLocalRandom.current().nextInt();
        }
        return iban;
    }

    @Override
    public boolean ibanCheck(String iban) {
        Account account = accountRepository.getAccountByIban(iban);
        if (account == null){
            return true;
        }
        return false;
    }

    @Override
    public Account getbyIban(String iban) {
        Account account = accountRepository.getAccountByIban(iban);
        if(Objects.isNull(account)){
            throw new IllegalStateException("Wrong Iban");
        }
        else {
            return account;
        }
    }

    @Override
    public int deposit(String iban, int amount) throws Exception {
        Account account = getbyIban(iban);
        BigDecimal newamount = account.getBalance().add(BigDecimal.valueOf(amount));
        if (account.getAccountType() != Account.AccountTypeEnum.CURRENT){
            throw new Exception("Account must be of type current");
        }
        else if (account != null) {
            Transaction transaction = new Transaction();
            transaction.setAccountfrom(account);
            transaction.setAmount(new BigDecimal(amount));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setUserperforming(account.getUser().getUserType());
            transaction.setAccountto("Deposit");
            transactionService.WithdrawDeposit(transaction);
            return accountRepository.updateBalance(newamount, account.getIban());
        }
        else {
            throw new Exception("incorrect iban");
        }
    }

    @Override
    public Account withdraw (String iban, int amount) throws Exception {
        Account account = getbyIban(iban);
        BigDecimal withdrawAmount = account.getBalance().subtract(BigDecimal.valueOf(amount));
        if (account.getAccountType() != Account.AccountTypeEnum.CURRENT){
            throw new Exception("Account must be of type current");
        }
        else if (withdrawAmount.compareTo(BigDecimal.ZERO)<0){
            throw new Exception("Balance too low");
        }
        else if(account!= null){
            accountRepository.updateBalance(withdrawAmount,account.getIban());
            Transaction transaction = new Transaction();
            transaction.setAccountfrom(account);
            transaction.setAmount(new BigDecimal(amount));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setUserperforming(account.getUser().getUserType());
            transaction.setAccountto("Withdraw");
            transactionService.WithdrawDeposit(transaction);
            return getbyIban(iban);
        }
        else {
            throw new Exception("incorrect iban");
        }
    }

    @Override
    public List<Account> getAllUserByUserId(Integer userId) {
        List<Account> accounts =accountRepository.getAllByUserid(userId);
        if(accounts != null){
        return accounts;
        }
        else{
            throw new AccountNotFoundException(HttpStatus.NOT_FOUND, "Account is not found");
        }
    }
    @Override
    public void closeAccount(String iban){
        Account account = getbyIban(iban);
        if(account!=null){
            accountRepository.delete(account);
        }
        else{
            throw new EntityNotFoundException("Account does not exit");
        }

    }

    @Override
    public int updateAmount(String iban, BigDecimal amount) throws Exception {
        Account account = getbyIban(iban);
        account.setBalance(account.getBalance().subtract(amount));
        if (account.getBalance().compareTo(BigDecimal.ZERO)<0){
            throw new Exception("Account does not have enough balance");
        }
        return accountRepository.updateBalance(account.getBalance(),account.getIban());
    }

    @Override
    public int increaseAmount(String iban, BigDecimal amount) {
        Account account = getbyIban(iban);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.updateBalance(account.getBalance(),account.getIban());
    }

    @Override
    public boolean accountCheck(String iban1, String iban2) {
        Account accountFrom = getbyIban(iban1);
        Account accountTo = getbyIban(iban2);
        if (accountFrom.getUser().getUserId() == accountTo.getUser().getUserId()){
            return true;
        }
        else {
            return false;
        }
    }
}
