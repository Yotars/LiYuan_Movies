package com.liyuan.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.liyuan.db.entity.Room;
import com.liyuan.db.find.RoomFind;
import com.liyuan.db.mapper.RoomMapper;
import com.liyuan.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * (Room)表服务层
 *
 * @author Yotars
 * @since 2024-01-10 19:09:52
 */
@Service
public class RoomService {

    @Autowired
    private RoomMapper m;

    private MapUtil map = new MapUtil();

    /**
     * 查询 Room 表
     *
     * @param find {RoomFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(RoomFind find) {
    
        Page page = new Page<>(find.getIndex(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Room>()
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
     * 插入 Room 表数据
     *
     * @param room {Room} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Room room) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        row = m.insert(room);

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
     * 修改 Room 表数据
     *
     * @param room {Room} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Room room) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        row = m.updateById(room);

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
     * 删除 Room 表数据
     *
     * @param room {Room} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map delete(Room room) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(room);

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
