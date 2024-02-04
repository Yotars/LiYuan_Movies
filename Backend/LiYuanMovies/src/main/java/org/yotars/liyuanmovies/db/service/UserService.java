package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import org.yotars.liyuanmovies.db.entity.User;
import org.yotars.liyuanmovies.db.find.UserFind;
import org.yotars.liyuanmovies.db.mapper.UserMapper;
import org.yotars.liyuanmovies.util.MapUtil;
import org.yotars.liyuanmovies.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author Yotars
 * @since 2024-01-06 16:02:10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper m;

    @Autowired
    private InfoService info;

    private TokenUtil tokenUtil = new TokenUtil();

    private MapUtil map = new MapUtil();

    /**
     * 用户登陆
     *
     * @param user {User} 用户表
     * @return  map {Map} 登陆的结果
     */
    public Map login(User user) {

        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*")
                .eq("username", user.getUsername())
                .eq("password", user.getPassword());

        User u = m.selectOne(wrapper);
        boolean is = u.getUId() != null;
        String token = tokenUtil.getToken(u);
        String message = "";

        return map.outMap("200", is, token, message);
    }

    /**
     * 验证用户权限
     *
     * @param token {String} 传入token
     * @return isAdmin {Boolean} 验证结果
     */
    public Boolean isAdmin(String token) {
        return tokenUtil.isAdmin(token);
    }

    /**
     * 查询 user 表
     *
     * @param find {UserFind} 要查询的数据
     * @return map {Map} 查询的结果
     */
    public Map list(UserFind find) {
    
        Page page = new Page<>(find.getCurrent(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<User>()
                .select("*");

        IPage p = m.selectJoinMapsPage(page,wrapper);

        HashMap pages = new HashMap<String, Object>();
        pages.put("current", p.getCurrent());
        pages.put("size", p.getSize());
        pages.put("pages", p.getPages());
        pages.put("total", p.getTotal());

        HashMap result = new HashMap<String, Object>();
        result.put("records", p.getRecords());
        result.put("pagination", pages);

        String message = "已查询到" + p.getTotal() +"条数据";

        return map.outMap("200", result, message);
    }

    /**
     * 插入 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 插入的结果
     */
    public Map insert(User user) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        user.setPower(1);

        if (checkName(user.getUId(), user.getUsername())) {
            row = 0;
            is = false;
            massage = "用户名已存在";
        }
        else {
            row = m.insert(user);

            info.insert(user.getUId());

            if (row != 0) {
                is = true;
                massage += "成功";
            }
            else {
                is = false;
                massage += "失败";
            }
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 修改 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 修改的结果
     */
    public Map update(User user) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        if (checkName(user.getUId(), user.getUsername())) {
            row = 0;
            is = false;
            massage = "用户名已存在";
        }
        else {
            row = m.updateById(user);
            if (row != 0) {
                is = true;
                massage += "成功";
            }
            else {
                is = false;
                massage += "失败";
            }
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 删除 User 表数据
     *
     * @param user {User} 用户表
     * @return  map {Map} 删除的结果
     */
    public Map delete(User user) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(user.getUId());
        info.delete(user.getUId());
        if (row != 0) {
            is = true;
            massage += "成功";
        }
        else {
            is = false;
            massage += "删除";
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 检查命名是否已存在
     *
     * @param id {Integer} id
     * @param name {String} name
     * @return checkName {Boolean} 检查结果
     */
    private Boolean checkName(Integer id, String name) {
        QueryWrapper wrapper = new QueryWrapper<User>()
                .select("*")
                .ne("uId", id)
                .eq("username", name);
        return m.selectMaps(wrapper).size() != 0;
    }
}

