package com.example.highconcurrencypraise.repository;

import com.example.highconcurrencypraise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
