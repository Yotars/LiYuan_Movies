package com.liyuan.db.entity;

import lombok.Data;

import java.sql.Date;

/**
 * (Oder)实体类
 *
 * @author Yotars
 * @since 2024-01-11 15:45:06
 */
@Data
public class Oder {
    /**
     * 订单ID
     */
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
     * 订单创建时间
     */
    private Date oDate;
    /**
     * 订单状态: 0-待处理 1-已完成
     */
    private Integer oStatus;
}
