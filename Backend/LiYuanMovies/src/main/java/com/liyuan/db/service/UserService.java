package com.liyuan.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.User;
import com.liyuan.db.find.UserFind;
import com.liyuan.db.mapper.UserMapper;
import com.liyuan.util.MapUtil;
import com.liyuan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private TokenUtil tokenUtil = new TokenUtil();

    private MapUtil map = null;

    /**
     * 查询 user 表
     *
     * @param find {UserFind} 要查询的数据
     * @return map {Map} 查询的结果
     *
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    public Map list(UserFind find) {
    
        Page page = new Page<>(find.getIndex(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*");

        map = new MapUtil();

        return map.setMap("200", m.selectJoinMapsPage(page,wrapper));
    }

    /**
     * 插入 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 插入的结果
     *
     *   {
     *     "status":"200",
     *     "result":
     *     {
     *       "is": "插入的结果",
     *       "row": "插入的条数",
     *       "message": "信息"
     *     }
     *   }
     */
    public Map insert(User user) {

        Boolean is = false;
        Integer row = 0;
        String massage = "插入";

        row = m.insert(user);

        if (row != 0) {
            is = true;
            massage = massage + "成功";
        }
        else {
            is = false;
            massage = massage + "失败";
        }
        return map.setMap("200", is, row, massage);
    }

    /**
     * 修改 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 修改的结果
     *
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
    public Map update(User user) {

        Boolean is = false;
        Integer row = 0;
        String massage = "修改";

        row = m.updateById(user);

        if (row != 0) {
            is = true;
            massage = massage + "成功";
        }
        else {
            is = false;
            massage = massage + "失败";
        }
        return map.setMap("200", is, row, massage);
    }

    /**
     * 删除 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 删除的结果
     *
     *   {
     *     "status":"200",
     *     "result":
     *     {
     *       "is": "删除的结果",
     *       "row": "删除的条数",
     *       "message": "信息"
     *     }
     *   }
     */
    public Map delete(User user) {

        map = new MapUtil();

        Boolean is = false;
        Integer row = 0;
        String massage = "删除";

        row = m.deleteById(user);

        if (row != 0) {
            is = true;
            massage = massage + "成功";
        }
        else {
            is = false;
            massage = massage + "删除";
        }
        return map.setMap("200", is, row, massage);
    }

    /**
     * 用户登陆
     *
     * @param user {User} 用户表
     * @return  map {Map} 登陆的结果
     *
     *   {
     *     "status":"200",
     *     "result":
     *     {
     *       "is": "登陆的结果",
     *       "token": "加密后的token"
     *     }
     *   }
     */
    public Map login(User user) {

        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*")
                .eq("username", user.getUsername())
                .eq("password", user.getPassword());

        map = new MapUtil();

        User u = m.selectOne(wrapper);
        Boolean is = u.getUid() != null;
        String token = tokenUtil.getToken(u);

        return map.setMap("200", is, token);
    }
}
