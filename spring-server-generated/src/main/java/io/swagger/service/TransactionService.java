package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TransactionService {

     Transaction createTransaction(Transaction transaction) throws Exception;
     Transaction WithdrawDeposit(Transaction transaction);
     Transaction getTransactionsById(Integer transactionId) throws Exception;
    //public List<Transaction> getTransactionsByAccountID(String accountto) throws Exception;
     List<Transaction> getAllTransactions(String iban) throws Exception;
     List<Transaction> getAllTransactionsBetween2Months(String month1, String month2, User loggedInUser) throws Exception;
}
