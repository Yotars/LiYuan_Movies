package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import org.yotars.liyuanmovies.db.entity.Seat;
import org.yotars.liyuanmovies.db.find.SeatFind;
import org.yotars.liyuanmovies.db.mapper.SeatMapper;
import org.yotars.liyuanmovies.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private RoomService room;

    private MapUtil map = new MapUtil();

    /**
     * 查询 Seat 表
     *
     * @param find {SeatFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(SeatFind find) {

        MPJQueryWrapper wrapper = new MPJQueryWrapper<Seat>()
                .select("*");

        if (find.getRId() != null) {
            wrapper.eq("rId", find.getRId());
        }

        List p = m.selectJoinMaps(wrapper);

        HashMap result = new HashMap<String, Object>();
        result.put("records", p);
        
        String message = "已查询到" + p.size() +"条数据";

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

    /**
     * 修改座位状态
     *
     * @param sId {Integer} 座位id
     * @param status {Integer} 座位状态
     * @return seat {Seat} 返回修改后的 seat
     */
    public Map status(Integer sId, Integer status) {

        UpdateWrapper<Seat> set = new UpdateWrapper<Seat>()
                .eq("sId", sId)
                .set("status", status);
        m.update(set);

        MPJQueryWrapper get = new MPJQueryWrapper<Seat>()
                .select("t.*")
                .select("r.rName")
                .leftJoin("room r ON r.rId = t.rId")
                .eq("sId", sId);

        Map map = m.selectJoinMap(get);

        QueryWrapper count = new QueryWrapper<Seat>()
                .select("*")
                .eq("rId", map.get("rId"))
                .eq("status", 1);

        Long counts = m.selectCount(count);

        room.rest(counts, (Integer) map.get("rId"));

        return map;
    }
}
