package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yotars.liyuanmovies.db.entity.Info;
import org.yotars.liyuanmovies.db.find.InfoFind;
import org.yotars.liyuanmovies.db.mapper.InfoMapper;
import org.yotars.liyuanmovies.util.MapUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * (Info)表服务层
 *
 * @author Yotars
 * @since 2024-01-10 19:09:52
 */
@Service
public class InfoService {

    @Autowired
    private InfoMapper m;

    private MapUtil map = new MapUtil();

    /**
     * 查询 Info 表
     *
     * @param find {InfoFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(InfoFind find) {
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Info>()
                .select("*")
                .eq("uId", find.getUId());
                
        Map p = m.selectJoinMap(wrapper);

        HashMap result = new HashMap<String, Object>();
        result.put("records", p);
        
        String message = "已查询到1条数据";

        return map.outMap("200", result, message);
    }

    /**
     * 插入 Info 表数据
     *
     * @param uId {Integer} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Integer uId) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        Info info = new Info();
        info.setUId(uId);
        info.setNickname(rName());
        row = m.insert(info);

        if (row != 0) {
            is = true;
            massage += "成功";
        }
        else {
            is = false;
            massage += "失败";
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 修改 Info 表数据
     *
     * @param info {Info} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Info info) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        if (checkName(info.getUId(), info.getNickname())) {
            row = 0;
            is = false;
            massage = "命名已存在";
        }
        else {
            row = m.updateById(info);
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
     * 删除 Info 表数据
     *
     * @param info {Info} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map delete(Info info) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        System.out.println(info.getUId());
        row = m.deleteById(info);
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
     * 生成随机用户名
     *
     * @return rName {String} 返回的用户名
     */
    private String rName() {
        Random random = new Random();
        String r = "新用户" + (random.nextLong(100000000) + 100000000);
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Info>()
                .select("*")
                .eq("nickname", r);

        if (m.selectCount(wrapper) == 0) {
            return r;
        }
        else {
            return rName();
        }
    }

    /**
     * 检查命名是否已存在
     *
     * @param id {Integer} id
     * @param name {String} name
     * @return checkName {Boolean} 检查结果
     */
    private Boolean checkName(Integer id, String name) {
        QueryWrapper wrapper = new QueryWrapper<Info>()
                .select("*")
                .ne("uId", id)
                .eq("nickname", name);
        return m.selectMaps(wrapper).size() != 0;
    }
}
