package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        List<Transaction> transactionList = getAllTransactions(transaction.getAccountfrom().getIban());
        for (Transaction t: transactionList
             ) {
            sum.add(t.getAmount());
        }
        if (sum.compareTo(transaction.getDayLimit())>0){
            transactionRepository.UpdateDayLimit(transaction.getDayLimit().subtract(sum), transaction.getAccountfrom().getIban());
            return false;
        }
        transactionRepository.UpdateDayLimit(transaction.getDayLimit().subtract(sum), transaction.getAccountfrom().getIban());
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
        transactionRepository.save(transaction);
        accountService.updateAmount(transaction.getAccountfrom().getIban(), transaction.getAmount());
        accountService.increaseAmount(transaction.getAccountto(), transaction.getAmount());
        return transaction;
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
        List<Transaction> transactionList = transactionRepository.findAll();
        List<Transaction> returnTransactions = new ArrayList<>();
        for(Transaction t: transactionList
        ) {
            if (t.getAccountto().contains(iban)){
                returnTransactions.add(t);
            }
        }
        return returnTransactions;
    }

}
