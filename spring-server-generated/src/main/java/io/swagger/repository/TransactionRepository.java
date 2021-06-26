package io.swagger.repository;

import io.swagger.model.CreateTransaction;
import io.swagger.model.Transactions;
import io.swagger.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    Transactions getTransactionById(Integer id);
    //List<Transactions> getTransactions(Integer userID, OffsetDateTime start, OffsetDateTime end);


}
