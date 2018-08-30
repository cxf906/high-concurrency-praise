package com.example.highconcurrencypraise.service.impl;

import com.example.highconcurrencypraise.model.UserMoodPraiseRel;
import com.example.highconcurrencypraise.repository.UserMoodPraiseRelRepository;
import com.example.highconcurrencypraise.service.UserMoodPraiseRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserMoodPraiseRelServiceImpl implements UserMoodPraiseRelService {
    @Resource
    private UserMoodPraiseRelRepository userMoodPraiseRelRepository;

    @Override
    public void save(UserMoodPraiseRel userMoodPraiseRel) {
        userMoodPraiseRelRepository.save(userMoodPraiseRel);
    }
}
