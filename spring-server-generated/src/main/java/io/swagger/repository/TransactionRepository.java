package io.swagger.repository;

import io.swagger.model.CreateTransaction;
import io.swagger.model.Transactions;
import io.swagger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {
    @Query( "Select t from Transactions t where id=?1")
    Transactions getTransactionById(Integer id);

    //List<Transactions> getTransactionWithParameters(Integer userID, OffsetDateTime dateFrom, OffsetDateTime dateTo);


}