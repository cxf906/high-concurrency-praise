package com.example.highconcurrencypraise.service;

import com.example.highconcurrencypraise.dto.MoodDTO;
import com.example.highconcurrencypraise.model.Mood;

import java.util.List;

public interface MoodService {

    List<MoodDTO> findAll();

    List<MoodDTO> findAllForRedis();

    boolean praiseMood(String userId,String moodId);

    boolean praiseMoodForRedis(String userId,String moodId);

    Mood find(String id);

    void update(Mood mood);

}
