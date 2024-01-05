package com.liyuan.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.User;
import com.liyuan.db.find.UserFind;
import com.liyuan.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-01-05 16:31:28
 */
@Service
public class UserService {

    @Autowired
    private UserMapper m;
   
    public Map list(UserFind find) {
    
        Page page = new Page<>(find.getIndex(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*");
                
        HashMap<String, Object> map = new HashMap();
        map.put("status", "200");
        map.put("data", m.selectJoinMapsPage(page,wrapper));
        
        return map;
    }
    
    public Map insert(User user) {
    
        HashMap<String, Object> map = new HashMap();
        map.put("status", "200");
        map.put("data", m.insert(user) != 0);
        
        return map;
    }
    
    public Map update(User user) {
    
        HashMap<String, Object> map = new HashMap();
        map.put("status", "200");
        map.put("data", m.updateById(user) != 0);
        
        return map;
    }
    
    public Map delete(User user) {
        
        HashMap<String, Object> map = new HashMap();
        map.put("status", "200");
        map.put("data", m.deleteById(user) != 0);
        
        return map;
    }
}
