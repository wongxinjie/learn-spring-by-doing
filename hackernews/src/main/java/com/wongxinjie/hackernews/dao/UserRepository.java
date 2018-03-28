package com.wongxinjie.hackernews.dao;

import com.wongxinjie.hackernews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    User findFirstByEmail(String email);

    Boolean existsByIdNotAndUsername(@Param("id") Long id, @Param("username") String username);
}
