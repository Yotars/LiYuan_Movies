package org.yotars.liyuanmovies.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * (Room)实体类
 *
 * @author Yotars
 * @since 2024-01-13 15:35:06
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
    private String start;
    /**
     * 结束时间
     */
    private String end;
    /**
     * 座位总数
     */
    private Integer rTotal;
    /**
     * 剩余座位
     */
    private Integer rest;
}
