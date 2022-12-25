package com.yogo.repository;

import com.yogo.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user ORDER BY id_user DESC LIMIT 1", nativeQuery = true)
    User findLastUser();

    User findByEmail(String email);

    List<User> findByRole(Role role);
}
