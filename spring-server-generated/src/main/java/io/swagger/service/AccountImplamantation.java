package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountImplamantation implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void save(Account account) throws Exception {
        String setiban = ibanFormat();
        account.setIban(setiban);
        if (account.getUser() != null) {
            accountRepository.save(account);
        }
        else {
            throw new Exception("incrorrect user");
        }
    }

    @Override
    public List<Account> GetAccountbyName(String name) {
        List<Account> accounts = accountRepository.getAllByName(name);
        if (accounts.isEmpty()){
            throw new EntityNotFoundException("Wrong Name");
        }
        return accounts;
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
            return getbyIban(iban);
        }
        else {
            throw new Exception("incorrect iban");
        }
    }

    @Override
    public List<Account> getAllByUser(Integer userId) throws Exception {
        List<Account> accounts =accountRepository.getAllByUserid(userId);
        if(accounts!=null){
        return accounts;
        }else{
            throw new Exception("Accounts can not be retrieved");
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

    public void dequan(Account account, BigDecimal newBalance){
        BigDecimal oldBalance = account.getBalance();
        newBalance = oldBalance.subtract(newBalance);
        accountRepository.updateBalance(newBalance, account.getIban());
    }

    public void updateReceivingAccountBalance(Account account, BigDecimal newBalance){
        BigDecimal oldBalance = account.getBalance();
        newBalance = oldBalance.add(newBalance);
        accountRepository.updateBalance(newBalance, account.getIban());
    }

}
