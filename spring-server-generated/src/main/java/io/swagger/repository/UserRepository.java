package io.swagger.repository;

import io.swagger.model.User;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByEmail(String email);
    User findUserByUsername(String userName);
    User findUserByUserId(int userId);

    @Query("SELECT u from User as u left JOIN Account as a on u.userId = a.user.userId where a.user.userId is null ")
    Iterable<User> getAllByAccountsFalse();
}
