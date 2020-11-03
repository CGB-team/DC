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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

   /* @Override
    public JSONObject updatePayType(JSONObject requestJson) {
        String orderPayType = requestJson.getString("order_paytype");
        String orderNum = requestJson.getString("order_num");
        orderDao.updatePayType(orderNum,orderPayType);
        return CommonUtil.successJson();

    }*/

    @Override
    public JSONObject updateItemNum(JSONObject requestJson,String userTicket) {
        String wait_id = "在sys_user里找到跟dc_user关联的字段";
        Long user_id = 2232L; //从dc_user对象中找到user_id
        cartDao.updateItemNum(requestJson);
        Long itemId = requestJson.getLong("item_id");
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
        order.setOrderNum(orderNum);
        Integer orderTotal = (int)(requestJson.getDouble("cart_total")*100);
        order.setOrderTotal(orderTotal);
        orderDao.insert(order);

        JSONArray cartsArr = requestJson.getJSONArray("cartList");
        for(int i=0; i< cartsArr.size(); i++){
            Cart cartTemp = (Cart) cartsArr.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(cartTemp.getItemId());
            orderItem.setItemName(cartTemp.getItemName());
            orderItem.setItemNum(cartTemp.getItemNum());
            orderItem.setItemPrice(cartTemp.getItemPrice()*100);
            orderItem.setOrderNum(orderNum);
            orderItemDao.insert(orderItem);
        }
        QueryWrapper<OrderItem> qw = new QueryWrapper<>();
        qw.eq("order_num",orderNum);
        List<OrderItem> orderItems = orderItemDao.selectList(qw);
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
        System.out.println(total);
        return CommonUtil.successJson(total);
    }
}
