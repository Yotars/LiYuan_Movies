package com.liyuan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liyuan.db.entity.User;
import com.liyuan.db.find.UserFind;
import com.liyuan.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2024-01-05 01:32:07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService s;

    @GetMapping
    public IPage list(UserFind find) {
        return s.list(find);
    }
    
    @PostMapping
    public boolean insert(User user) {
        return s.insert(user);
    }
    
    @PutMapping
    public boolean update(User user) {
        return s.update(user);
    }
    
    @DeleteMapping
    public boolean delete(User user) {
        return s.delete(user);
    }
}

