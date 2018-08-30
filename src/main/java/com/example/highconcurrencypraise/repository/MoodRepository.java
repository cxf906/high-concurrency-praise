package com.example.highconcurrencypraise.repository;

import com.example.highconcurrencypraise.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood,String> {

}
