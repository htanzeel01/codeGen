package io.swagger.repository;

import io.swagger.model.CreateTransaction;
import io.swagger.model.Transactions;
import io.swagger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {

}
