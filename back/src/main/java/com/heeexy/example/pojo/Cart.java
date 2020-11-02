package com.heeexy.example.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("dc_cart")
@Accessors(chain = true)
public class Cart extends BasePojo{
    @TableId
    private Long cartId;

    private Long userId;
    private Long itemId;

    private String itemName;
    private String itemInfo;
    private Long itemPrice;
    private Integer itemNum;
    private Integer deleteState;

}
