package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/loadCartList")
    public JSONObject loadOrders(){
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String userTicket = userInfo.getString("userId");
        return orderService.loadCart(userTicket);
    }

    @PostMapping("/updateItemNum")
    public JSONObject updateItemNum(@RequestBody JSONObject requestJson){
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String userTicket = userInfo.getString("wait_id");
        return orderService.updateItemNum(requestJson,userTicket);
    }

    @PostMapping("/submitOrder")
    public JSONObject submitOrder(@RequestBody JSONObject requestJson){
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        CommonUtil.hasAllRequired(requestJson, "cartTotal,orderPaytype");
        return orderService.submitOrder(requestJson,username);
    }

    @PostMapping("/totalPrice")
    public JSONObject totalPrice(){
        return orderService.totalPrice();
    }



}
