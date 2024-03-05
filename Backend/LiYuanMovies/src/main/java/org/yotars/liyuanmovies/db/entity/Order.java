package org.yotars.liyuanmovies.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (Order)实体类
 *
 * @author Yotars
 * @since 2024-01-12 23:56:30
 */
@Data
@TableName("`order`")
public class Order {
    /**
     * 订单ID
     */
    @TableId(value = "oId", type = IdType.AUTO)
    private Integer oId;
    /**
     * 用户ID
     */
    private Integer uId;
    /**
     * 订单编号
     */
    private String oNumber;
    /**
     * 订单详情
     */
    private String oDetails;
    /**
     * 订单信息
     */
    private String oMessage;
    /**
     * 订单创建时间
     */
    private String oDate;
    /**
     * 订单状态: 0-已取消 1-待处理 2-已完成
     */
    private Integer oStatus;
}
