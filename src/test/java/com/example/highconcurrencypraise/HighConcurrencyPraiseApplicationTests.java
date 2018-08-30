package com.example.highconcurrencypraise;

import com.example.highconcurrencypraise.model.User;
import com.example.highconcurrencypraise.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HighConcurrencyPraiseApplicationTests {

    @Resource
    private UserRepository uerRepository;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Test
    public void contextLoads() {
        List<User> list =  uerRepository.findAll();
        System.out.println();
    }

}
