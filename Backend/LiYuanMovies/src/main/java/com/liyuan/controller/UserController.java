package com.liyuan.controller;

import com.liyuan.db.entity.User;
import com.liyuan.db.find.UserFind;
import com.liyuan.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2024-01-05 16:31:28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService s;

    @GetMapping
    public Map list(UserFind find) {
        return s.list(find);
    }
    
    @PostMapping
    public Map insert(User user) {
        return s.insert(user);
    }
    
    @PutMapping
    public Map update(User user) {
        return s.update(user);
    }
    
    @DeleteMapping
    public Map delete(User user) {
        return s.delete(user);
    }
}

