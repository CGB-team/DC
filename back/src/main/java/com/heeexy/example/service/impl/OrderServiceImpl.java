package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.heeexy.example.dao.CartDao;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.dao.OrderItemDao;
import com.heeexy.example.dao.OrderUserDao;
import com.heeexy.example.pojo.Cart;
import com.heeexy.example.pojo.Order;
import com.heeexy.example.pojo.OrderItem;
import com.heeexy.example.pojo.OrderUser;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.service.UserService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderUserDao orderUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CartDao cartDao;

    @Override
    public JSONObject loadCart(String userTicket) {
        String wait_id = "在sys_user里找到跟dc_user关联的字段";
        Long user_id = 2232L; //从dc_user对象中找到user_id
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("user_id",user_id);
        List<Cart> carts = cartDao.selectList(qw);
        return CommonUtil.successJson(carts);
    }

    @Override
    public JSONObject updateItemNum(JSONObject requestJson,String userTicket) {
        String wait_id = "在sys_user里找到跟dc_user关联的字段";
        Long user_id = 2232L; //从dc_user对象中找到user_id
        Long itemId = requestJson.getLong("itemId");
        Integer itemNum = requestJson.getInteger("itemNum");
        cartDao.updateItemNum(itemId,itemNum);
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("item_id", itemId);
        Cart cart = cartDao.selectOne(qw);
        Long total = cart.getItemPrice()*cart.getItemNum();
        return CommonUtil.successJson(total);
    }

    @Override
    public JSONObject submitOrder(JSONObject requestJson,String username) {
        String wait_id = "在sys_user里找到跟dc_user关联的字段";
        Long user_id = 2232L; //从dc_user对象中找到user_id
        Order order = new Order();
        order.setOrderUserId(user_id);
        String orderNum = user_id+""+System.currentTimeMillis();
        System.out.println(orderNum);
        order.setOrderNum(orderNum);
        Integer orderTotal = requestJson.getInteger("cartTotal");
        order.setOrderTotal(orderTotal);
        String orderPayType = requestJson.getString("orderPaytype");
        order.setOrderPaytype(orderPayType);
        orderDao.insert(order);

        QueryWrapper<Cart> qw1 = new QueryWrapper<>();
        qw1.eq("user_id",user_id);
        List<Cart> carts = cartDao.selectList(qw1);
        for(Cart cart:carts){
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(cart.getItemId());
            orderItem.setItemName(cart.getItemName());
            orderItem.setItemPrice(cart.getItemPrice());
            orderItem.setItemNum(cart.getItemNum());
            orderItem.setOrderNum(orderNum);
            orderItemDao.insert(orderItem);
        }

        QueryWrapper<OrderItem> qw2 = new QueryWrapper<>();
        qw2.eq("order_num",orderNum);
        List<OrderItem> orderItems = orderItemDao.selectList(qw2);
        order.setOrderItems(orderItems);
        return CommonUtil.successJson(order);
    }

    @Override
    public JSONObject totalPrice() {
        Long total = 0L;
        Long user_id = 2232L; //从dc_user对象中找到user_id
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("user_id",user_id);
        List<Cart> carts = cartDao.selectList(qw);
        for(int i=0; i< carts.size(); i++){
            Cart cartTemp = carts.get(i);
            total += cartTemp.getItemPrice()*cartTemp.getItemNum();
        }
        return CommonUtil.successJson(total);
    }
}
