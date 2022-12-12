package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user ORDER BY id_user DESC LIMIT 1", nativeQuery = true)
    User findLastUser();

    User findByEmail(String email);
}
