package io.swagger.service;

import io.swagger.repository.TransactionRepository;
import io.swagger.model.Account;
import io.swagger.model.AccountResult;
import io.swagger.model.CreateTransaction;
import io.swagger.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void createTransaction(Account account, CreateTransaction body){
        Transactions transaction = new Transactions();
        transaction.setFrom(account.getIban());
        transaction.setTo(body.getTo());
        transaction.setAmount(body.getAmount());
        transaction.setUserPerforming(account.getUser().getFirstName());
        transaction.setTransactionDate(OffsetDateTime.now().toString());
        transactionRepository.save(transaction);

    }
    public Transactions getTransactionByID(Integer id){
        return transactionRepository.getTransactionById(id);
    }

   /*public List<Transactions> getTransactionswithParam(Integer userId, OffsetDateTime dateFrom, OffsetDateTime dateTo){
        return transactionRepository.getTransactionswithParam(userId, dateFrom, dateTo);
   }*/
}
