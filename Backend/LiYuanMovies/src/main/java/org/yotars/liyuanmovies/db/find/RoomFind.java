package org.yotars.liyuanmovies.db.find;

import lombok.Data;


/**
 * (Room)查询类
 *
 * @author Yotars
 * @since 2024-01-10 19:06:54
 */
@Data
public class RoomFind {
    private Integer current;
    private Integer size;
    private String mName;
    private String mId;
}
