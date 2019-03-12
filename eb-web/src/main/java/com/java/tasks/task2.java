package com.java.tasks;

import com.java.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * date:2019-02-23
 * 18:17
 * description:task2
 * author:潘全科
 */
@Component
public class task2 {
    @Autowired
    private IndexService indexService;
    @Scheduled(cron = "0 30 * * * *")
    public void doSeckillStep1(){
        indexService.processSeckill();
        System.out.println("Tesk2... 秒杀");
    }
    @Scheduled(cron = "0 30 * * * *")
    public void updateNoStart2Finish(){
        indexService.updateNoStart2Finish();
        //修改状态
    }
}
