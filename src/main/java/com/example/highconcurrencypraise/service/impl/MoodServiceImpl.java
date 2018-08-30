package com.example.highconcurrencypraise.service.impl;

import com.example.highconcurrencypraise.dto.MoodDTO;
import com.example.highconcurrencypraise.model.Mood;
import com.example.highconcurrencypraise.model.User;
import com.example.highconcurrencypraise.model.UserMoodPraiseRel;
import com.example.highconcurrencypraise.mq.MoodProducer;
import com.example.highconcurrencypraise.repository.MoodRepository;
import com.example.highconcurrencypraise.service.MoodService;
import com.example.highconcurrencypraise.service.UserMoodPraiseRelService;
import com.example.highconcurrencypraise.service.UserService;
import com.example.highconcurrencypraise.utils.UuidUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MoodServiceImpl implements MoodService {
    @Resource
    private MoodRepository moodRepository;
    @Resource
    private UserService uerService;
    @Resource
    private UserMoodPraiseRelService userMoodPraiseRelService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MoodProducer moodProducer;

    private static final String PRAISE_HASH_KEY="com.example.high.conc.key";

    private static Destination destination = new ActiveMQQueue("ay.quene.high.concurrency-praise");
    @Override
    public List<MoodDTO> findAll() {

        List<Mood> moodList = moodRepository.findAll();
        if(CollectionUtils.isEmpty(moodList)){
            return Collections.EMPTY_LIST;
        }

        List<MoodDTO> moodDTOList = new ArrayList<>();
        for(Mood mood : moodList){
            MoodDTO moodDTO = new MoodDTO();
            moodDTO.setId(mood.getId());
            moodDTO.setUserId(mood.getUserId());
            moodDTO.setContent(mood.getContent());
            moodDTO.setPraiseNum(mood.getPraiseNum());
            moodDTO.setPublishTime(mood.getPublishTime());
            User user = uerService.find(mood.getUserId());
            moodDTO.setUserName(user.getName());
            moodDTO.setUserAccount(user.getAccount());
            moodDTOList.add(moodDTO);
        }
        return moodDTOList;
    }

    @Override
    public List<MoodDTO> findAllForRedis() {
        List<Mood> moodList = moodRepository.findAll();
        if(CollectionUtils.isEmpty(moodList)){
            return Collections.EMPTY_LIST;
        }

        List<MoodDTO> moodDTOList = new ArrayList<>();
        for(Mood mood : moodList){
            MoodDTO moodDTO = new MoodDTO();
            moodDTO.setId(mood.getId());
            moodDTO.setUserId(mood.getUserId());
            moodDTO.setContent(mood.getContent());
            if(redisTemplate.opsForSet().members(mood.getId()) != null){
                moodDTO.setPraiseNum(mood.getPraiseNum()+redisTemplate.opsForSet().size(mood.getId()).intValue());
            }else{
                moodDTO.setPraiseNum(mood.getPraiseNum());
            }
            moodDTO.setPublishTime(mood.getPublishTime());
            User user = uerService.find(mood.getUserId());
            moodDTO.setUserName(user.getName());
            moodDTO.setUserAccount(user.getAccount());
            moodDTOList.add(moodDTO);
        }
        return moodDTOList;
    }

    @Override
    public boolean praiseMood(String userId, String moodId) {
        UserMoodPraiseRel userMoodPraiseRel = new UserMoodPraiseRel();
        userMoodPraiseRel.setId(UuidUtil.generateUUID());
        userMoodPraiseRel.setMoodId(moodId);
        userMoodPraiseRel.setUserId(userId);
        userMoodPraiseRelService.save(userMoodPraiseRel);

        Mood mood = moodRepository.getOne(moodId);
        mood.setPraiseNum(mood.getPraiseNum()+1);
        moodRepository.saveAndFlush(mood);
        return Boolean.TRUE;
    }

    @Override
    public boolean praiseMoodForRedis(String userId, String moodId) {
        Mood mood = new Mood();
        mood.setUserId(userId);
        mood.setId(moodId);
        moodProducer.sendMessage(destination,mood);

        //PRAISE_HASH_KEY存放所有的moodId，key：PRAISE_HASH_KEY，value：所有的moodId
        //redisTemplate.opsForSet().add(PRAISE_HASH_KEY,moodId);
        //key：moodId，value：点赞的人
        //redisTemplate.opsForSet().add(moodId,userId);
        return Boolean.TRUE;
    }

    @Override
    public Mood find(String id) {
        Mood mood = new Mood();
        mood.setId(id);
        Optional<Mood> moodOptional = moodRepository.findOne(Example.of(mood));
        return moodOptional.get();
    }

    @Override
    public void update(Mood mood) {
        moodRepository.save(mood);
    }
}
