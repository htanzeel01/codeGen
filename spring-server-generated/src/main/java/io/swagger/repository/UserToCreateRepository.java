package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToCreateRepository extends JpaRepository<User,Integer> {
    User getUserToCreateByEmail(String email);
    User findUserToCreateByUsername(String userName);
    User findUserToCreateByUserId(int userId);
}
