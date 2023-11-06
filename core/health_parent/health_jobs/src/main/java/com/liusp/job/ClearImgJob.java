package com.liusp.job;

import com.liusp.constant.RedisConstant;
import com.liusp.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author cr
 * @date 2023年11月01日 16:58
 * @description  定时清理 垃圾图片
 */
@Component //让spring帮我们创建这个对象
public class ClearImgJob {

    //调用redis
    @Autowired
    StringRedisTemplate redisTemplate;
    //每10秒执行一次
    @Scheduled(cron = "0/10 * * * * ?")
    public void clearImg(){
        //1.获取 redis的差集

        Set<String> set = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        //2.删除 垃圾图片
        for (String imgname : set) {
            QiniuUtils.deleteFileFromQiniu(imgname);
            //3.删除redis  RedisConstant.SETMEAL_PIC_RESOURCES key 里面的垃圾图片名
            redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_RESOURCES,imgname);
        }


    }

}
