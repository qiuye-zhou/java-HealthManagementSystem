package com.liusp.controller;

import com.aliyuncs.exceptions.ClientException;
import com.liusp.constant.MessageConstant;
import com.liusp.constant.RedisMessageConstant;
import com.liusp.entity.Result;
import com.liusp.utils.SMSUtils;
import com.liusp.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串的
    //体检预约时发送手机验证码
    @RequestMapping("/send4Order.do")
    public Result send4Order(String telephone){
        //阿里云工具类生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);//生成4位数字验证码
        //腾讯云工具类生成验证码
//        Integer code =Integer.valueOf(SmsComponent.randomCode());
        try {
            //发送短信
            //阿里云短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            //腾讯云短信
//            SmsComponent smsc=new SmsComponent();
//            smsc.sendSmsCode(telephone,code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis,第一个参数为key,第二个参数为验证码，第三个参数为seconds有效时间
        redisTemplate.opsForValue().set(
                telephone + RedisMessageConstant.SENDTYPE_ORDER,code.toString(),5 * 60, TimeUnit.SECONDS);
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    //手机快速登录时发送手机验证码
    @RequestMapping("/send4Login.do")
    public Result send4Login(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);//生成6位数字验证码
        try {
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis
        redisTemplate.opsForValue().set(
                telephone+ RedisMessageConstant.SENDTYPE_LOGIN,code.toString(),5 * 60, TimeUnit.SECONDS);
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}