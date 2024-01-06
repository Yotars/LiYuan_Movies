package com.liyuan.db.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.liyuan.db.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)数据访问层
 *
 * @author Yotars
 * @since 2024-01-06 16:00:33
 */
@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

}
