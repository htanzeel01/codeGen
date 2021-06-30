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
    Transactions findByAccountto(String iban);
    Transactions findTransactionsById(int Id);
    Iterable<Transactions> getAllByAccountto(String iban);
    @Query("SELECT t FROM Transactions AS t WHERE t.accountfrom LIKE ?1 OR t.accountto LIKE ?1 ORDER BY t.transactionDate ASC")
    Iterable<Transactions> getTransactionsByIban(String iban);

}
