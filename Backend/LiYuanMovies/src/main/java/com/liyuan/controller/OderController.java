package com.liyuan.controller;

import com.liyuan.db.entity.Oder;
import com.liyuan.db.find.OderFind;
import com.liyuan.db.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Oder)表控制层
 *
 * @author Yotars
 * @since 2024-01-11 15:45:07
 */
@RestController
@RequestMapping("/oder")
public class OderController {

    @Autowired
    private OderService s;

    /**
     * 查询 Oder 表
     *
     * @param find {OderFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(OderFind find) {
        return s.list(find);
    }

    /**
     * 插入 Oder 表数据
     *
     * @param oder {Oder} 传入的数据
     * @return map {Map} 返回的结果
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
    public Map insert(Oder oder, String token) {
        return s.insert(oder, token);
    }

    /**
     * 取消订单
     *
     * @param oder {Oder} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result":
     *   {
     *     "is": "结果",
     *     "row": "条数",
     *     "message": "信息"
     *   }
     * }
     */
    @PostMapping("/cancel")
    public Map cancel(Oder oder, String token) {
        return s.cancel(oder, token);
    }

    /**
     * 完成订单
     *
     * @param oder {Oder} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result":
     *   {
     *     "is": "结果",
     *     "row": "条数",
     *     "message": "信息"
     *   }
     * }
     */
    @PostMapping("/done")
    public Map done(Oder oder) {
        return s.done(oder);
    }

    /**
     * 修改 Oder 表数据
     *
     * @param oder {Oder} 传入的数据
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
     */
    @PutMapping
    public Map update(Oder oder) {
        return s.update(oder);
    }

    /**
     * 删除 Oder 表数据
     *
     * @param oder {Oder} 传入的数据
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
    public Map delete(Oder oder) {
        return s.delete(oder);
    }
}

