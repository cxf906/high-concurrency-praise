package com.example.highconcurrencypraise.mq;

import com.example.highconcurrencypraise.model.Mood;
import com.example.highconcurrencypraise.service.MoodService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MoodConsumer {
    @Resource
    private MoodService moodService;
    @Resource
    private RedisTemplate redisTemplate;

    private static final String PRAISE_HASH_KEY="com.example.high.conc.key";
    @JmsListener(destination = "ay.quene.high.concurrency-praise")
    public void receiveQuene(Mood ayMood){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //PRAISE_HASH_KEY存放所有的moodId，key：PRAISE_HASH_KEY，value：所有的moodId
        redisTemplate.opsForSet().add(PRAISE_HASH_KEY,ayMood.getId());
        //key：moodId，value：点赞的人
        redisTemplate.opsForSet().add(ayMood.getId(),ayMood.getUserId());
        System.out.println(1234);
    }

}
