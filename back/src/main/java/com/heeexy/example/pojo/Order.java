package com.heeexy.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@TableName("dc_order")
@Accessors(chain = true)
public class Order extends BasePojo{

    private OrderUser orderUser;
    private List<OrderItem> orderItems;

    @TableId
    private Long orderId;

    @TableField(exist = false)
    private String orderNum;

    private Integer orderTotal;
    private String orderInfo;
    private String orderPayType;
    private Integer orderState;
    private Long orderItemId;
    private Long orderUserId;
    private Integer deleteState;
}
