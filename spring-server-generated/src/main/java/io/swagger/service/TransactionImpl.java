package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.math.BigDecimal;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountService accountService;

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
                throw new Exception("Savings account can only tansfer to one of your accounts");
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
}
