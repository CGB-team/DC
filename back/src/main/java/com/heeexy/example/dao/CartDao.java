package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.pojo.Cart;
import org.springframework.web.bind.annotation.RequestParam;

public interface CartDao extends BaseMapper<Cart> {

    void updateItemNum(@RequestParam Long itemId,@RequestParam Integer itemNum);
}
