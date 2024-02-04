package org.yotars.liyuanmovies.db.find;

import lombok.Data;


/**
 * (Order)查询类
 *
 * @author Yotars
 * @since 2024-01-11 15:45:35
 */
@Data
public class OrderFind {
    private Integer current;
    private Integer size;
    private Integer uId;
    private String oNumber;
}
