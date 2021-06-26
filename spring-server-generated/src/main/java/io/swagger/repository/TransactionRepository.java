package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Integer> {
    Transactions findTransactionsById(int Id);

}
