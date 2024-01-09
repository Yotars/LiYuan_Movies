package com.liyuan.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  封装工具类
*
* @author Yotars
* @since 2024-01-06 15:25:48
*/
@Component
public class MapUtil {

    /**
     * 将查询的结果进行封装
     *
     * @param status {String} 状态值
     * @param result {Object} 请求结果
     * @return map {Map} 封装的结果
     */
    public Map<String, Object> outMap(String status, Page result, String message) {

        return setMap(status, result, message);
    }

    /**
     * 封装插入、修改、删除的结果
     *
     * @param status {String} 状态码
     * @param is {Boolean} 布尔值
     * @param row {Integer} 条数
     * @param message {String} 信息
     * @return map {Map} 封装的结果
     */
    public Map<String, Object> outMap(String status, Boolean is, Integer row, String message) {

        HashMap result = new HashMap<>();
        result.put("is", is);
        result.put("row", row);
        
        return setMap(status, result, message);
    }

    /**
     * 分装登陆的结果
     *
     * @param status {Integer} 状态码
     * @param is {Boolean} 布尔值
     * @param token {String} 加密的 token
     * @return map {Map} 封装的结果
     */
    public Map<String, Object> outMap(String status, Boolean is, String token, String message) {

        HashMap result = new HashMap<>();
        result.put("is", is);
        result.put("token", token);

        return setMap(status, result, message);
    }

    /**
     * 将结果进行最终分装
     * 
     * @param status {String} 状态值
     * @param result {Object} 结果
     * @return map {Map} 封装结果
     */
    private Map<String, Object> setMap(String status, Object result, String message) {
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("result", result);
        map.put("message", message);
        
        return map;
    }
}

