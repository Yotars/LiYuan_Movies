package com.liyuan.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.Movies;
import com.liyuan.db.find.MoviesFind;
import com.liyuan.db.mapper.MoviesMapper;
import com.liyuan.util.MapUtil;
import com.liyuan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * (Movies)表服务层
 *
 * @author Yotars
 * @since 2024-01-08 21:30:23
 */
@Service
public class MoviesService {

    @Autowired
    private MoviesMapper m;

    private MapUtil map =  new MapUtil();

    /**
     * 查询 Movies 表
     *
     * @param find {MoviesFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(MoviesFind find) {
    
        Page page = new Page<>(find.getIndex(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Movies>()
                .select("*");

        String arr[] = find.getType().split(",");

        for (int i = 0; i < arr.length; i++) {
            wrapper.like("type", arr[i]);
        }

        String message = "";

        return map.outMap("200", m.selectJoinMapsPage(page,wrapper), message);
    }

    /**
     * 插入 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        row = m.insert(movies);

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
     * 修改 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        row = m.updateById(movies);

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
     * 删除 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map delete(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(movies);

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
}
