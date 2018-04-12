package com.wongxinjie.hackernews.repository;

import com.wongxinjie.hackernews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long>{

    User findFirstByEmail(String email);

    Boolean existsByIdNotAndUsername(@Param("id") Long id, @Param("username") String username);
}
