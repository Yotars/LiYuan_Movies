package org.yotars.liyuanmovies.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * (Movies)实体类
 *
 * @author Yotars
 * @since 2024-01-10 18:49:52
 */
@Data
public class Movies {
    /**
     * 电影id
     */
    @TableId(value = "mId", type = IdType.AUTO)
    private Integer mId;
    /**
     * 电影名称
     */
    private String mName;
    /**
     * 电影图片
     */
    private String mImage;
    /**
     * 导演
     */
    private String director;
    /**
     * 演员
     */
    private String actors;
    /**
     * 电影类型
     */
    private String type;
    /**
     * 地区
     */
    private String area;
    /**
     * 语言
     */
    private String language;
    /**
     * 上映时间
     */
    private String launchDate;
    /**
     * 时长
     */
    private String filmLength;
    /**
     * IMDb
     */
    private String IMDb;
    /**
     * 简介
     */
    private String Intro;
}
