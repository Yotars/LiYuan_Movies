package com.liyuan.db.entity;

import lombok.Data;

/**
 * (User)实体类
 *
 * @author Yotars
 * @since 2024-01-10 18:49:51
 */
@Data
public class User {
    /**
     * 用户id
     */
    private Integer uId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户权限
     */
    private Integer power;
}
