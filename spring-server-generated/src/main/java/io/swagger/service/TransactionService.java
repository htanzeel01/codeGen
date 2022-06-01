package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.model.UserToCreate;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public interface TransactionService {

    public Transactions createTransaction(Transactions transactions) throws Exception;
    public Transactions WithdrawDeposit(Transactions transactions);
    public Transactions getTransactionsById(Integer transactionId) throws Exception;
    //public List<Transactions> getTransactionsByAccountID(String accountto) throws Exception;
    public List<Transactions> getAllTransactions(String iban) throws Exception;
}
