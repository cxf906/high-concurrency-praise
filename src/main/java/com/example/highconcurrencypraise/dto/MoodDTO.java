package com.example.highconcurrencypraise.dto;

import com.example.highconcurrencypraise.model.Mood;

public class MoodDTO extends Mood {

    private String userName;

    private String userAccount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
