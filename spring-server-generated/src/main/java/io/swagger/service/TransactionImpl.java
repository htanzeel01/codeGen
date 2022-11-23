package io.swagger.service;

import io.swagger.exception.UnAuthorizedException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Log
@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountService accountService;

    public boolean CheckTransactionDayLimit(Transaction transaction) throws Exception {
        BigDecimal sum = BigDecimal.ZERO;
        if(transaction == null){
            throw new Exception("Transaction is empty");
        }
        sum = sum.add(transaction.getAmount());
        List<Transaction> transactionList = getAllTransactions(transaction.getAccountfrom().getIban());
        for (Transaction t: transactionList
             ) {
            sum = sum.add(t.getAmount());
        }
        double sumed = sum.doubleValue();
        double limit = transaction.getDayLimit().doubleValue();
        if (sumed > limit){
            //transactionRepository.UpdateDayLimit(transaction.getDayLimit().subtract(sum), transaction.getAccountfrom().getIban());
            return false;
        }
        //transactionRepository.UpdateDayLimit(transaction.getDayLimit().subtract(sum), transaction.getAccountfrom().getIban());
        return true;
    }

    @Transactional
    @Override
    public Transaction createTransaction(Transaction transaction) throws Exception {
        transaction.setAccountfrom(accountService.getbyIban(transaction.getAccountfrom().getIban()));
        if (transaction.getAmount().compareTo(BigDecimal.valueOf(7000)) > 0) {
            throw new Exception("Amount cannot be more than transaction limit of 7000");
        }
        if (accountService.getbyIban(transaction.getAccountto()) == null) {
            return null;
        }
        if (transaction.getAccountfrom().getAccountType() == Account.AccountTypeEnum.SAVINGS) {
            if (!accountService.accountCheck(transaction.getAccountfrom().getIban(), transaction.getAccountto())) {
                throw new Exception("Savings account can only transfer to one of your accounts");
            }
        }
        else if (transaction.getAccountfrom().getAccountType() == Account.AccountTypeEnum.CURRENT) {
            Account account2 = accountService.getbyIban(transaction.getAccountto());
             {
                 if (transaction.getAccountfrom().getAccountType() != account2.getAccountType())
                throw new Exception("Savings account can only transfer to one of your accounts");
            }
        }
        if (CheckTransactionDayLimit(transaction)) {
            transactionRepository.save(transaction);
            accountService.updateAmount(transaction.getAccountfrom().getIban(), transaction.getAmount());
            accountService.increaseAmount(transaction.getAccountto(), transaction.getAmount());
            return transaction;
        }
        else{
            throw new Exception("Daily limit crossed");
        }
    }

    @Override
    public Transaction WithdrawDeposit(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionsById(Integer transactionId) throws Exception {
            Transaction transaction = transactionRepository.findTransactionsById(transactionId);
            if(transaction !=null){
                return transaction;
            }
            else {
                throw new Exception("Transations can not be found");
            }
    }

    @Override
    public List<Transaction> getAllTransactions(String iban) {
        List<Transaction> transactionList = (List<Transaction>) transactionRepository.findAllByAccountfrom_Iban(iban);
        List<Transaction> returnTransactions = new ArrayList<>();
        for(Transaction t: transactionList
        ) {
            if (t.getAccountfrom().getIban().contains(iban)){
                if (t.getTransactionDate().toLocalDate().equals(LocalDate.now()))
                returnTransactions.add(t);
            }
        }
        return returnTransactions;
    }

    @Override
    public List<Transaction> getAllTransactionsBetween2Months(String month1, String month2, User loggedInUser) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date1 = LocalDate.parse(month1,formatter);
        LocalDate date2 = LocalDate.parse(month2,formatter);
        Account account = accountService.getAccountbyUserId(loggedInUser);
        List<Transaction> transactionList = transactionRepository.findAllByAccountfrom_Iban(account.getIban());
        List<Transaction> monthlyTransactions = new ArrayList<>();
        for (Transaction t: transactionList
             ) {
            if (t.getTransactionDate().getMonth().equals(date1.getMonth()) || t.getTransactionDate().getMonth().equals(date2.getMonth())){
                monthlyTransactions.add(t);
            }
        }
        return monthlyTransactions;
    }
}
