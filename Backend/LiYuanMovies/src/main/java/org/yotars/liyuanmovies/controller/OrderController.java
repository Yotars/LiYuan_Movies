package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.Order;
import org.yotars.liyuanmovies.db.find.OrderFind;
import org.yotars.liyuanmovies.db.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Order)表控制层
 *
 * @author Yotars
 * @since 2024-01-11 15:45:07
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService s;

    /**
     * 查询 Order 表
     *
     * @param find {OrderFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(OrderFind find) {
        return s.list(find);
    }

    /**
     * 插入 Order 表数据
     *
     * @param order {Order} 传入的数据
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
    public Map insert(Order order) {
        return s.insert(order);
    }

    /**
     * 取消订单
     *
     * @param order {Order} 传入的数据
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
    public Map cancel(Order order) {
        return s.cancel(order);
    }

    /**
     * 完成订单
     *
     * @param order {Order} 传入的数据
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
    public Map done(Order order) {
        return s.done(order);
    }

    /**
     * 修改 Order 表数据
     *
     * @param order {Order} 传入的数据
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
    public Map update(Order order) {
        return s.update(order);
    }

    /**
     * 删除 Order 表数据
     *
     * @param order {Order} 传入的数据
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
    public Map delete(Order order) {
        return s.delete(order);
    }
}

