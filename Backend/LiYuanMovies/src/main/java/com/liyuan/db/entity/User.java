package com.liyuan.db.entity;

import lombok.Data;

import java.sql.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2024-01-05 16:31:28
 */
@Data
public class User {
        private Integer uid;
        private String username;
        private String password;
        private Integer power;
}

