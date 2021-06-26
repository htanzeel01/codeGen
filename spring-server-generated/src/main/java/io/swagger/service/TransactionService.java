package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.model.UserToCreate;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.time.LocalDateTime;

@Service
public interface TransactionService {
    public Transactions createTransaction(Transactions transactions) throws Exception;
    public Transactions WithdrawDeposit(Transactions transactions);
    public Transactions getTransactionsById(Integer transactionId) throws Exception;

}
