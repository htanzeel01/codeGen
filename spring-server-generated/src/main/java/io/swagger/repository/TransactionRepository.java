package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findByAccountto(String iban);
    Transaction findTransactionsById(int Id);
    Iterable<Transaction> getAllByAccountto(String iban);
    @Query("SELECT t FROM Transaction AS t WHERE t.accountfrom LIKE ?1 OR t.accountto LIKE ?1 ORDER BY t.transactionDate ASC")
    Iterable<Transaction> getTransactionsByIban(String iban);
//    @Query ("SELECT SUM (amount) from Transaction  AS t where t.accountfrom.iban = ?1 AND t.transactionDate = current_date ")
//    int SumTransaction (String iban);

    @Transactional
    @Modifying
    @Query("update Transaction set dayLimit = ?1 where accountfrom.iban = ?2")
    int UpdateDayLimit (BigDecimal limit, String iban);

    @Query("SELECT t FROM Transaction AS t WHERE t.accountfrom.iban = ?1")
    List<Transaction> findAllByAccountfrom_Iban(String iban);
}
