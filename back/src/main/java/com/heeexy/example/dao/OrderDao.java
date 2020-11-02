package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.pojo.Order;
import com.heeexy.example.pojo.OrderItem;
import com.heeexy.example.pojo.OrderUser;
import org.apache.ibatis.annotations.Param;

public interface OrderDao extends BaseMapper<Order> {

    int updatePayType(@Param("orderId") Integer orderId, @Param("orderPayType") String orderPayType);


}
