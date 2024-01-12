package com.liyuan.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * (Seat)实体类
 *
 * @author Yotars
 * @since 2024-01-11 23:31:10
 */
@Data
public class Seat {
    /**
     * 座位ID
     */
    @TableId(value = "sId", type = IdType.AUTO)
    private Integer sId;
    /**
     * 放映室ID
     */
    private Integer rId;
    /**
     * 座位状态: 0-未选择 1-已选择
     */
    private Integer status;
    /**
     * 座位: 行
     */
    private Integer r;
    /**
     * 座位: 列
     */
    private Integer l;
}
