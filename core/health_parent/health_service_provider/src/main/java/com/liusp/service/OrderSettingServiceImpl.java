package com.liusp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.liusp.dao.OrderSettingDao;
import com.liusp.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    //批量添加
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
    //根据日期查询预约设置数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {//2023-11
        //查询本月从1号到31号的 数据  1 3 5 7 8 10 12 都包含31天  469 11 都是30 天   2月份 平年 28天 闰年29天
        String beginTime = date+"-1";
        String endTime ="";
        String m =date.substring(date.lastIndexOf("-")+1);//获取月份
        if(m.equals("4")||m.equals("6")||m.equals("9")||m.equals("11")){
            //说明这个月份 最大日期是30天
            endTime= date+"-30";
        }else{
            //TODO 如果月份是2的话 还需要 判断是平年 闰年 平年 28天 闰年29天
            endTime = date+"-31";
        }

        List<OrderSetting> list=  orderSettingDao.getOrderSettingByMonth(beginTime,endTime);

        //将数据重新封装
        List<Map> orderSettingMap = new ArrayList<>();

        for (OrderSetting orderSetting : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());//获取日期 day
            map.put("number",orderSetting.getNumber());//设置可预约人数
            map.put("reservations",orderSetting.getReservations());//设置已预约人数
            orderSettingMap.add(map);
        }
        return orderSettingMap;
    }
    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}