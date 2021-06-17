package io.swagger.repository;

import io.swagger.model.UserToCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToCreateRepository extends JpaRepository<UserToCreate,Integer> {
    UserToCreate getUserToCreateByEmail(String email);
    UserToCreate findUserToCreateByUsername(String userName);
    UserToCreate findUserToCreateByUserId(int userId);
}
