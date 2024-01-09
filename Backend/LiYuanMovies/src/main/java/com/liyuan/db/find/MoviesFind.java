package com.liyuan.db.find;

import lombok.Data;


/**
 * (Movies)实体类
 *
 * @author Yotars
 * @since 2024-01-08 21:30:23
 */
@Data
public class MoviesFind {
    private Integer index;
    private Integer size;
    private String mName;
    private String type;
    private String area;
    private String launchDate;
}
