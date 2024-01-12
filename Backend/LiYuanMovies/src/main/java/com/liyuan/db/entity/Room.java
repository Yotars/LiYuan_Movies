package com.liyuan.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * (Room)实体类
 *
 * @author Yotars
 * @since 2024-01-11 15:42:20
 */
@Data
public class Room {
    /**
     * 放映厅id
     */
    @TableId(value = "rId", type = IdType.AUTO)
    private Integer rId;
    /**
     * 放映厅名称
     */
    private String rName;
    /**
     * 电影id
     */
    private Integer mId;
    /**
     * 开始时间
     */
    private Date start;
    /**
     * 结束时间
     */
    private Date end;
    /**
     * 座位总数
     */
    private Integer sTotal;
    /**
     * 剩余座位
     */
    private Integer sRest;
}
