package com.example.highconcurrencypraise.service;

import com.example.highconcurrencypraise.model.User;

import java.util.Optional;

public interface UserService {
    User find(String id);
}
