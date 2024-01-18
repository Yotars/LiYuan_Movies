package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.Seat;
import org.yotars.liyuanmovies.db.find.SeatFind;
import org.yotars.liyuanmovies.db.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Seat)表控制层
 *
 * @author Yotars
 * @since 2024-01-10 19:06:34
 */
@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService s;

    /**
     * 查询 Seat 表
     *
     * @param find {SeatFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(SeatFind find) {
        return s.list(find);
    }

    /**
     * 插入 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
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
    public Map insert(Seat seat) {
        return s.insert(seat);
    }
    
    /**
     * 修改 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
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
    public Map update(Seat seat) {
        return s.update(seat);
    }

    /**
     * 删除 Seat 表数据
     *
     * @param seat {Seat} 传入的数据
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
    public Map delete(Seat seat) {
        return s.delete(seat);
    }
}

