package io.swagger.service;

import io.swagger.model.Transactions;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TransactionService {

     Transactions createTransaction(Transactions transactions) throws Exception;
     Transactions WithdrawDeposit(Transactions transactions);
     Transactions getTransactionsById(Integer transactionId) throws Exception;
    //public List<Transactions> getTransactionsByAccountID(String accountto) throws Exception;
     List<Transactions> getAllTransactions(String iban) throws Exception;
}
