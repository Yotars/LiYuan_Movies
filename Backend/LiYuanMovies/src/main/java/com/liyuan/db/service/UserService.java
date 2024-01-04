package com.liyuan.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.User;
import com.liyuan.db.find.UserFind;
import com.liyuan.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-01-05 01:52:19
 */
@Service
public class UserService {

    @Autowired
    private UserMapper m;
   
    public IPage list(UserFind find) {
        Page page = new Page<>(find.getIndex(), find.getSize());
        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*");
        return m.selectJoinMapsPage(page,wrapper);
    }
    
    public boolean insert(User user) {
        return m.insert(user) != 0;
    }
    
    public boolean update(User user) {
        return m.updateById(user) != 0;
    }
    
    public boolean delete(User user) {
        return m.deleteById(user) != 0;
    }
}
