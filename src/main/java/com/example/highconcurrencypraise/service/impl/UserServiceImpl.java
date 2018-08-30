package com.example.highconcurrencypraise.service.impl;

import com.example.highconcurrencypraise.model.User;
import com.example.highconcurrencypraise.repository.UserRepository;
import com.example.highconcurrencypraise.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User find(String id) {
        return userRepository.getOne(id);
    }
}
