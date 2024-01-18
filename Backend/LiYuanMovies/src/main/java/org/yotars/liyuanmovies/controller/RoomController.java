package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.Room;
import org.yotars.liyuanmovies.db.find.RoomFind;
import org.yotars.liyuanmovies.db.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Room)表控制层
 *
 * @author Yotars
 * @since 2024-01-10 19:06:34
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService s;

    /**
     * 查询 Room 表
     *
     * @param find {RoomFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(RoomFind find) {
        return s.list(find);
    }

    /**
     * 插入 Room 表数据
     *
     * @param room {Room} 传入的数据
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
    public Map insert(Room room) {
        return s.insert(room);
    }
    
    /**
     * 修改 Room 表数据
     *
     * @param room {Room} 传入的数据
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
    public Map update(Room room) {
        return s.update(room);
    }

    /**
     * 删除 Room 表数据
     *
     * @param room {Room} 传入的数据
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
    public Map delete(Room room) {
        return s.delete(room);
    }
}

