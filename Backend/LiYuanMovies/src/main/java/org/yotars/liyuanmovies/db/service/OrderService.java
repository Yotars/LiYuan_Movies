package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import org.yotars.liyuanmovies.db.entity.Order;
import org.yotars.liyuanmovies.db.find.OrderFind;
import org.yotars.liyuanmovies.db.mapper.OrderMapper;
import org.yotars.liyuanmovies.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * (Order)表服务层
 *
 * @author Yotars
 * @since 2024-01-11 15:45:06
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper m;

    @Autowired
    private SeatService seat;

    private MapUtil map = new MapUtil();

    /**
     * 查询 Order 表
     *
     * @param find {OrderFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(OrderFind find) {

        Page page = new Page<>(find.getCurrent(), find.getSize());
        
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Order>()
                .select("*")
                .like("oNumber", find.getONumber());
        if (find.getUId() != null) {
            wrapper.eq("uId", find.getUId());
        }
                
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
     * 插入 Order 表数据
     *
     * @param order {Order} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map insert(Order order) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        String[]number = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

        order.setOStatus(1);
        order.setONumber(rNumber());
        order.setUId(order.getUId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setODate(sdf.format(System.currentTimeMillis()));
        String[] sIds = order.getODetails().split(",");
        Map seats = null;
        String oMessage = "一共有"+ sIds.length +"张票, ";
        for (int i = 0; i < sIds.length; i++) {
            seats = seat.status(Integer.valueOf(sIds[i]), 1);
            oMessage += seats.get("rName")+
                    ": " + number[(int) seats.get("l")] +
                    "排" + number[(int) seats.get("r")] + "列, ";
        }
        order.setOMessage(oMessage);

        row = m.insert(order);
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
     * 取消订单
     *
     * @param order {Order} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map cancel(Order order) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        order.setOStatus(0);

        String[] sIds = order.getODetails().split(",");
        for (int i = 0; i < sIds.length; i++) {
            seat.status(Integer.valueOf(sIds[i]), 0);
        }

        row = m.updateById(order);
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
     * 完成订单
     *
     * @param order {Order} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map done(Order order) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        order.setOStatus(3);

        row = m.updateById(order);
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
     * 修改 Order 表数据
     *
     * @param order {Order} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Order order) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        row = m.updateById(order);

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
     * 删除 Order 表数据
     *
     * @param order {Order} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map delete(Order order) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(order);

        if (row != 0) {
            String[] sIds = order.getODetails().split(",");
            for (int i = 0; i < sIds.length; i++) {
                seat.status(Integer.valueOf(sIds[i]), 0);
            }
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
     * 生成随机订单号
     *
     * @return rNumber {String} 生成的订单号
     */
    private String rNumber() {
        Random random = new Random();
        String r = "LY" + (random.nextLong(10000000) + 10000000);
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Order>()
                .select("*")
                .eq("oNumber", r);

        if (m.selectCount(wrapper) == 0) {
            return r;
        }
        else {
            return rNumber();
        }
    }
}
