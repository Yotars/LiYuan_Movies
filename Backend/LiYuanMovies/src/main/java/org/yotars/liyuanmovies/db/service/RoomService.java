package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import org.yotars.liyuanmovies.db.entity.Room;
import org.yotars.liyuanmovies.db.find.RoomFind;
import org.yotars.liyuanmovies.db.mapper.RoomMapper;
import org.yotars.liyuanmovies.util.MapUtil;
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
    
        Page page = new Page<>(find.getCurrent(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Room>()
                .select("t.*, m.mName")
                .leftJoin("movies m ON t.mId = m.mId")
                .like("m.mName", find.getMName());
                
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
     * 插入 Room 表数据
     *
     * @param room {Room} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Room room) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        if (checkName(room.getRId(), room.getRName())) {
            row = 0;
            is = false;
            massage = "名称已存在";
        }
        else  {
            row = m.insert(room);

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
     * 修改 Room 表数据
     *
     * @param room {Room} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Room room) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        if (checkName(room.getRId(), room.getRName())) {
            row = 0;
            is = false;
            massage = "名称已存在";
        }
        else {
            row = m.updateById(room);

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

    /**
     * 修改剩余座位数
     *
     * @param count {Long} 已占用座位数
     * @param rId {int} 放映厅id
     */
    public void rest(Long count, int rId) {
        QueryWrapper query = new QueryWrapper<Room>()
                .select("*")
                .eq("rId", rId);
        Room room = m.selectOne(query);

        UpdateWrapper update = new UpdateWrapper<Room>()
                .eq("rId", rId)
                .set("rest", room.getRTotal() - count);
        m.update(update);
    }

    /**
     * 检查命名是否已存在
     *
     * @param id {Integer} id
     * @param name {String} name
     * @return checkName {Boolean} 检查结果
     */
    private Boolean checkName(Integer id, String name) {
        QueryWrapper wrapper = new QueryWrapper<Room>()
                .select("*")
                .ne("rId", id)
                .eq("rName", name);
        return m.selectMaps(wrapper).size() != 0;
    }
}
