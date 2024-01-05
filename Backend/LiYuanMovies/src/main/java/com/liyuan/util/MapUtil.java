package com.liyuan.util;


import java.util.HashMap;
import java.util.Map;


public class MapUtil {

    /**
     * 将查询的结果进行封装
     *
     * @param status {String} 状态值
     * @param result {Object} 请求结果
     * @return map {Map} 封装的结果
     */
    public Map<String, Object> setMap(String status, Object result) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("result", result);

        return map;
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
    public Map<String, Object> setMap(String status, Boolean is, Integer row, String message) {

        HashMap result = new HashMap<>();
        result.put("is", is);
        result.put("row", row);
        result.put("message", message);

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("result", result);

        return map;
    }

    /**
     * 分装登陆的结果
     *
     * @param status {Integer} 状态码
     * @param is {Boolean} 布尔值
     * @param token {String} 加密的 token
     * @return map {Map} 封装的结果
     */
    public Map<String, Object> setMap(String status, Boolean is, String token) {

        HashMap result = new HashMap<>();
        result.put("is", is);
        result.put("token", token);

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("result", result);

        return map;
    }
}
