package com.liusp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liusp.constant.MessageConstant;
import com.liusp.entity.Result;
import com.liusp.pojo.OrderSetting;
import com.liusp.service.OrderSettingService;
import com.liusp.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cr
 * @date 2023年11月02日 9:55
 * @description  设置可预约人数 控制层
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {


    @Reference
    OrderSettingService orderSettingService;


    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){

        //1.解析 当前表格内容
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if(list!=null && list.size()>0){
                //2.将解析的内容放入 Ordersetting 对象当中
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] strings : list) { //[["2023/11/3","200"] ,["2023/11/4","201"].... ]

                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1]));
                    orderSettings.add(orderSetting);
                }

                //3.TODO调用业务接口实现 批量数据插入
                orderSettingService.add(orderSettings);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }


    //根据 年月查询 当前月份 每天的 可预约人数及已预约人数

    @RequestMapping("/getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(String date){
        //执行业务功能完成查询
        try {
            List<Map>  list =   orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    //修改可预约人数 editNumberByDate
    @RequestMapping("/editNumberByDate.do")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            //预约设置成功
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            //预约设置失败
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
