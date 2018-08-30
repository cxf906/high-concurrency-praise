package com.example.highconcurrencypraise.controller;

import com.example.highconcurrencypraise.dto.MoodDTO;
import com.example.highconcurrencypraise.service.MoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/mood")
public class MoodController {

    @Resource
    private MoodService moodService;
    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<MoodDTO> moodDTOList = moodService.findAll();
        model.addAttribute("moods",moodDTOList);
        model.addAttribute("name","cxf");
        return "mood";
    }
    @RequestMapping("/praise")
    public String praise(Model model, @RequestParam(value = "moodId") String moodId,
                         @RequestParam(value = "userId") String userId){
        boolean isPraise = moodService.praiseMood(userId,moodId);

        List<MoodDTO> moodDTOList = moodService.findAll();
        model.addAttribute("moods",moodDTOList);
        model.addAttribute("name","cxf");
        return "mood";
    }

    @RequestMapping("/praiseForRedis")
    public String praiseForRedis(Model model, @RequestParam(value = "moodId") String moodId,
                         @RequestParam(value = "userId") String userId){
        Random random = new Random();
        userId = random.nextInt()+"";
        boolean isPraise = moodService.praiseMoodForRedis(userId,moodId);

        List<MoodDTO> moodDTOList = moodService.findAllForRedis();
        model.addAttribute("moods",moodDTOList);
        model.addAttribute("name","cxf");
        return "mood";
    }

}
