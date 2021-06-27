package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Integer> {
    Transactions findTransactionsById(int Id);
    List<Transactions> getAllTransactions();

    @Query("select t from Transactions t where to=?1")
    List<Transactions> getTransactionsByAccountID(String accountTo);

}
