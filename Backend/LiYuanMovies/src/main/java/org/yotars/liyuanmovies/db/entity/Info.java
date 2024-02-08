package org.yotars.liyuanmovies.db.entity;

import lombok.Data;

import java.sql.Date;

/**
 * (Info)实体类
 *
 * @author Yotars
 * @since 2024-01-10 18:54:07
 */
@Data
public class Info {
    /**
     * 用户id
     */
    private Integer uId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String head;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户生日
     */
    private Date birth;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
}
