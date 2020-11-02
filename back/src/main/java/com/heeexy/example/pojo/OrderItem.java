package com.heeexy.example.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("dc_order_iteminfo")
@Accessors(chain = true)
public class OrderItem extends BasePojo {

    @TableId
    private Long otId;

    private Long itemId;

    private String itemName;

    private Long itemPrice;

    private Integer itemNum;

    private String orderNum;







}
