package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.pojo.Cart;

public interface CartDao extends BaseMapper<Cart> {

    void updateItemNum(JSONObject requestJson);
}
