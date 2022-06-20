package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByEmail(String email);
    User findUserByUsername(String userName);
    User findUserByUserId(int userId);
}
