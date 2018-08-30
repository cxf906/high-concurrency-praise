package com.example.highconcurrencypraise.job;

import com.example.highconcurrencypraise.model.Mood;
import com.example.highconcurrencypraise.model.User;
import com.example.highconcurrencypraise.model.UserMoodPraiseRel;
import com.example.highconcurrencypraise.service.MoodService;
import com.example.highconcurrencypraise.service.UserMoodPraiseRelService;
import com.example.highconcurrencypraise.service.UserService;
import com.example.highconcurrencypraise.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

@Component
@Configurable
@EnableScheduling
public class PraiseDataSaveDBTasks {
    @Resource
    private MoodService moodService;
    @Resource
    private UserService userService;
    @Resource
    private UserMoodPraiseRelService userMoodPraiseRelService;
    @Resource
    private RedisTemplate redisTemplate;

    private static final String PRAISE_HASH_KEY="com.example.high.conc.key";

    //每5秒执行一次
    @Scheduled(cron = "*/5 * * * * *")
    public void savePraiseDataToDB(){
        System.out.println("run......");
    }

    //每10秒执行一次
    @Scheduled(cron = "*/10 * * * * *")
    public void savePraiseDataToDB2(){
        Set<String> moods = redisTemplate.opsForSet().members(PRAISE_HASH_KEY);
        if (CollectionUtils.isEmpty(moods)){
            return;
        }
        for (String moodId : moods){
            if(redisTemplate.opsForSet().members(moodId) == null){
                continue;
            }else{
                Set<String> userIds = redisTemplate.opsForSet().members(moodId);
                if (CollectionUtils.isEmpty(userIds)){
                    return;
                }else{
                    for (String userId : userIds){
                        UserMoodPraiseRel userMoodPraiseRel = new UserMoodPraiseRel();
                        userMoodPraiseRel.setId(UuidUtil.generateUUID());
                        userMoodPraiseRel.setUserId(userId);
                        userMoodPraiseRel.setMoodId(moodId);
                        userMoodPraiseRelService.save(userMoodPraiseRel);
                    }
                    Mood mood = moodService.find("1");
                    System.out.println(redisTemplate.opsForSet().size(moodId).intValue());
                    mood.setPraiseNum(mood.getPraiseNum() + redisTemplate.opsForSet().size(moodId).intValue());
                    moodService.update(mood);
                    redisTemplate.delete(moodId);
                }
            }
        }
        redisTemplate.delete(PRAISE_HASH_KEY);
    }
}
