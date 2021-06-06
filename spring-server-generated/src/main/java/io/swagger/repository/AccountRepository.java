package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    Account getAccountByName(String name);
    @Query(value = "Select * from Account where USER_ID=?1",nativeQuery = true)
    List<Account> getAllByUserid(int userId);

    Account getAccountByIban(String iban);
    @Transactional
    @Modifying
    @Query("update Account a set balance = ?1 where IBAN = ?2")
    int updateBalance(BigDecimal balance, String iban);

    List<Account> getAllByName(String name);

}
