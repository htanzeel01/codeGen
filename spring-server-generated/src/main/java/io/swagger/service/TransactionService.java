package io.swagger.service;

import io.swagger.model.Transactions;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;

@Service
public interface TransactionService {
    public Transactions createTransaction(Transactions transactions) throws Exception;
    public Transactions WithdrawDeposit(Transactions transactions);
}
