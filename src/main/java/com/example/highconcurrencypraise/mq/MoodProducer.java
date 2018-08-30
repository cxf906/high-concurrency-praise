package com.example.highconcurrencypraise.mq;

import com.example.highconcurrencypraise.model.Mood;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.jms.Destination;

@Component
public class MoodProducer {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    public void sendMessage(Destination destination, final Mood ayMood){
        jmsMessagingTemplate.convertAndSend(destination,ayMood);
    }

}
