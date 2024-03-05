package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.User;
import org.yotars.liyuanmovies.db.find.UserFind;
import org.yotars.liyuanmovies.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (User)表控制层
 *
 * @author Yotars
 * @since 2024-01-06 16:00:33
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService s;

    /**
     * 用户登陆
     *
     * @param user {User} 用户表
     * @return  map {Map} 登陆的结果
     * {
     *   "status": "200",
     *   "result": 
     *   {
     *     "token": "加密后的token"
     *   }
     * }
     */
    @PostMapping("/login")
    public Map login(User user) {
        return s.login(user);
    }

    /**
     * 验证用户权限
     *
     * @param token {String} 传入token
     * @return isAdmin {Boolean} 验证结果
     */
    @PostMapping("/isAdmin")
    public Map isAdmin(String token) {
        return s.isAdmin(token);
    }

    /**
     * 查询 user 表
     *
     * @param find {UserFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果",
     *   "message": "信息"
     * }
     */
    @GetMapping
    public Map list(UserFind find) {
        return s.list(find);
    }

    /**
     * 插入 User 表数据
     *
     * @param user {User} 传入的数据
     * @return  map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result":
     *   {
     *     "is": "插入的结果",
     *     "row": "插入的条数",
     *     "message": "信息"
     *   }
     * }
     */
    @PostMapping
    public Map insert(User user) {
        return s.insert(user);
    }
    
    /**
     * 修改 User 表数据
     *
     * @param user {User} 传入的数据
     * @return  map {Map} 返回的结果
     *   {
     *     "status":"200",
     *     "result":
     *     {
     *       "is": "修改的结果",
     *       "row": "修改的条数",
     *       "message": "信息"
     *     }
     *   }
     *
     */
    @PutMapping
    public Map update(User user) {
        return s.update(user);
    }

    /**
     * 删除 User 表数据
     *
     * @param user {User} 传入的数据
     * @return  map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result":
     *   {
     *     "is": "删除的结果",
     *     "row": "删除的条数",
     *     "message": "信息"
     *   }
     * }
     */
    @DeleteMapping
    public Map delete(User user) {
        return s.delete(user);
    }
}
