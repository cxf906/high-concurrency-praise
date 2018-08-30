package com.example.highconcurrencypraise.utils;

import java.util.UUID;

public class UuidUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","").toString();
    }
}
