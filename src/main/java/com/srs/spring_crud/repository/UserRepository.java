package com.srs.spring_crud.repository;

import com.srs.spring_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
}
