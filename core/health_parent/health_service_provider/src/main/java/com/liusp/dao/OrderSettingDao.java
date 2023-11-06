package com.liusp.dao;

import com.liusp.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    //更新可预约人数
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    //根据日期范围查询预约设置信息
    List<OrderSetting> getOrderSettingByMonth(@Param("beginTime") String beginTime,@Param("endTime") String endTime);


    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(Date orderDate);
}