package com.liyuan.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.Seat;
import com.liyuan.db.find.SeatFind;
import com.liyuan.db.mapper.SeatMapper;
import com.liyuan.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * (Seat)表服务层
 *
 * @author Yotars
 * @since 2024-01-10 19:09:52
 */
@Service
public class SeatService {

    @Autowired
    private SeatMapper m;

    private MapUtil map = new MapUtil();

    /**
     * 查询 Seat 表
     *
     * @param find {SeatFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(SeatFind find) {
    
        Page page = new Page<>(find.getIndex(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Seat>()
                .select("*");
                
        IPage p = m.selectJoinMapsPage(page,wrapper);

        HashMap result = new HashMap<String, Object>();
        result.put("records", p.getRecords());
        result.put("current", p.getCurrent());
        result.put("size", p.getSize());
        result.put("pages", p.getPages());
        result.put("total", p.getTotal());
        
        String message = "已查询到" + p.getTotal() +"条数据";

        return map.outMap("200", result, message);
    }

    /**
     * 插入 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Seat seat) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        row = m.insert(seat);

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
     * 修改 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Seat seat) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        row = m.updateById(seat);

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
     * 删除 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map delete(Seat seat) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(seat);

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
