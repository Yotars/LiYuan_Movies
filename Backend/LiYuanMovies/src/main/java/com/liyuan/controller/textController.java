package com.liyuan.controller;

import com.liyuan.db.entity.User;
import com.liyuan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class textController {

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping
    public User getUser(String token) {
        return tokenUtil.parseToken(token);
    }
}
