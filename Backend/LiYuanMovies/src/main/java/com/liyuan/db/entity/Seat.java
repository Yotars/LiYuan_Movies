package com.liyuan.db.entity;

import lombok.Data;


/**
 * (Seat)实体类
 *
 * @author Yotars
 * @since 2024-01-11 15:44:20
 */
@Data
public class Seat {
    /**
     * 座位id
     */
    private Integer sId;
    /**
     * 座位状态: 0-未选择 1-已选择
     */
    private Integer status;
    /**
     * 放映厅id
     */
    private Integer rId;
}
