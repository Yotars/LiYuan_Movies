package org.yotars.liyuanmovies.controller;

import org.yotars.liyuanmovies.db.entity.Movies;
import org.yotars.liyuanmovies.db.find.MoviesFind;
import org.yotars.liyuanmovies.db.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (Movies)表控制层
 *
 * @author Yotars
 * @since 2024-01-08 21:30:23
 */
@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService s;

    /**
     * 查询 Movies 表
     *
     * @param find {MoviesFind} 传入的数据
     * @return map {Map} 返回的结果
     * {
     *   "status":"200",
     *   "result": "查询结果"
     * }
     */
    @GetMapping
    public Map list(MoviesFind find) {
        return s.list(find);
    }

    /**
     * 插入 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
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
    public Map insert(Movies movies) {
        return s.insert(movies);
    }
    
    /**
     * 修改 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
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
    public Map update(Movies movies) {
        return s.update(movies);
    }

    /**
     * 删除 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
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
    public Map delete(Movies movies) {
        return s.delete(movies);
    }
}

