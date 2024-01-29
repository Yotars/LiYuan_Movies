package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.Info;
import org.yotars.liyuanmovies.db.find.InfoFind;
import org.yotars.liyuanmovies.db.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Info)表控制层
 *
 * @author Yotars
 * @since 2024-01-10 19:06:34
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private InfoService s;

    /**
     * 查询 Info 表
     *
     * @param find {InfoFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(InfoFind find) {
        return s.list(find);
    }
    
    /**
     * 修改 Info 表数据
     *
     * @param info {Info} 传入的数据
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
    public Map update(Info info) {
        return s.update(info);
    }

    /**
     * 删除 Info 表数据
     *
     * @param info {Info} 传入的数据
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
    public Map delete(Info info) {
        return s.delete(info.getUId());
    }
}

