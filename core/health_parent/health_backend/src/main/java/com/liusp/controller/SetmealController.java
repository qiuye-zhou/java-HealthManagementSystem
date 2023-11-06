package com.liusp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liusp.constant.MessageConstant;
import com.liusp.constant.RedisConstant;
import com.liusp.entity.PageResult;
import com.liusp.entity.QueryPageBean;
import com.liusp.entity.Result;
import com.liusp.pojo.Setmeal;
import com.liusp.service.SetmealService;
import com.liusp.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author cr
 * @date 2023年11月01日 10:22
 * @description 套餐控制类
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串的
    @Reference
    SetmealService setmealService;

    //上传功能
    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try {
            //1.获取原文件名
            String filename = imgFile.getOriginalFilename();
            //2.获取文件后缀   123.jpg
            int lastindex = filename.lastIndexOf(".");
            String suffix = filename.substring(lastindex-1);//
            //3.重新生成 文件名
            String name =  UUID.randomUUID().toString()+suffix;
            //4.使用工具类将图片上传到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),name);
            //5.将 新的文件名返回给前端


            //将上传图片名称存入Redis，基于Redis的Set集合存储
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,name);

            return  new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,name);
        } catch (IOException e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    //新增套餐
    @RequestMapping("/add.do")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try{
            setmealService.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }


    //分页查询
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }
}
