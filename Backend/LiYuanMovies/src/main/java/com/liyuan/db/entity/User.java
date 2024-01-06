package com.liyuan.db.entity;

import lombok.Data;

/**
 * (User)实体类
 *
 * @author Yotars
 * @since 2024-01-06 17:19:01
 */
@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
    private Integer power;
}
