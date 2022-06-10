package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Log
@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountService accountService;

    public boolean CheckTransactionDayLimit(Transactions transactions) throws Exception {
        BigDecimal sum = BigDecimal.ZERO;
        if(transactions == null){
            throw new Exception("Transaction is empty");
        }
        List<Transactions> transactionsList = getAllTransactions(transactions.getAccountfrom().getIban());
        for (Transactions t: transactionsList
             ) {
            sum.add(t.getAmount());
        }
        if (sum.compareTo(transactions.getDayLimit())>0){
            transactionRepository.UpdateDayLimit(transactions.getDayLimit().subtract(sum), transactions.getAccountfrom().getIban());
            return false;
        }
        transactionRepository.UpdateDayLimit(transactions.getDayLimit().subtract(sum), transactions.getAccountfrom().getIban());
        return true;
    }

    @Transactional
    @Override
    public Transactions createTransaction(Transactions transactions) throws Exception {
        if (transactions.getAmount().compareTo(BigDecimal.valueOf(7000)) > 0) {
            throw new Exception("Amount cannot be more than transaction limit of 7000");
        }
        if (accountService.getbyIban(transactions.getAccountto()) == null) {
            return null;
        }
        if (transactions.getAccountfrom().getAccountType() == Account.AccountTypeEnum.SAVINGS) {
            if (!accountService.accountCheck(transactions.getAccountfrom().getIban(), transactions.getAccountto())) {
                throw new Exception("Savings account can only transfer to one of your accounts");
            }
        }
        transactionRepository.save(transactions);
        accountService.updateAmount(transactions.getAccountfrom().getIban(), transactions.getAmount());
        accountService.increaseAmount(transactions.getAccountto(), transactions.getAmount());
        return transactions;
    }

    @Override
    public Transactions WithdrawDeposit(Transactions transactions) {
        return transactionRepository.save(transactions);
    }

    @Override
    public Transactions getTransactionsById(Integer transactionId) throws Exception {
            Transactions transactions = transactionRepository.findTransactionsById(transactionId);
            if(transactions!=null){
                return transactions;
            }
            else {
                throw new Exception("Transations can not be found");
            }
    }

    @Override
    public List<Transactions> getAllTransactions(String iban) {
        List<Transactions> transactionsList = transactionRepository.findAll();
        List<Transactions> returnTransactions = new ArrayList<>();
        for(Transactions t: transactionsList
        ) {
            if (t.getAccountto().contains(iban)){
                returnTransactions.add(t);
            }
        }
        return returnTransactions;
    }

}
