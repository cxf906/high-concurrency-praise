package com.example.highconcurrencypraise.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserMoodPraiseRel {
    @Id
    private String id;
    private String userId;
    private String moodId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoodId() {
        return moodId;
    }

    public void setMoodId(String moodId) {
        this.moodId = moodId;
    }
}
