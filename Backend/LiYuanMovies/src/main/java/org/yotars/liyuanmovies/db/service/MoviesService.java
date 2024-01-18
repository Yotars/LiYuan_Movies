package org.yotars.liyuanmovies.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import org.yotars.liyuanmovies.db.entity.Movies;
import org.yotars.liyuanmovies.db.entity.Type;
import org.yotars.liyuanmovies.db.find.MoviesFind;
import org.yotars.liyuanmovies.db.mapper.MoviesMapper;
import org.yotars.liyuanmovies.db.mapper.TypeMapper;
import org.yotars.liyuanmovies.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * (Movies)表服务层
 *
 * @author Yotars
 * @since 2024-01-08 21:30:23
 */
@Service
public class MoviesService {

    @Autowired
    private MoviesMapper m;

    @Autowired
    private TypeMapper type;

    private MapUtil map =  new MapUtil();

    /**
     * 查询 Movies 表
     *
     * @param find {MoviesFind} 传入的数据
     * @return map {Map} 返回的结果
     */
    public Map list(MoviesFind find) {

//        创建一个分页对象
        Page page = new Page<>(find.getIndex(), find.getSize());

//        数据查询语句
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Movies>()
                .select("*")
                .like("mName", find.getMName())
                .like("launchDate", find.getLaunchDate());

//        将传过来的type进行拼接查询
        String[]arr = find.getType().split(",");
        for (int i = 0; i < arr.length; i++) {
            wrapper.like("type", arr[i]);
        }

        IPage p = m.selectJoinMapsPage(page,wrapper);

        HashMap result = new HashMap<String, Object>();
        result.put("records", p.getRecords());
        result.put("current", p.getCurrent());
        result.put("size", p.getSize());
        result.put("pages", p.getPages());
        result.put("total", p.getTotal());
        result.put("types", types());

//        查询信息
        String message = "已查询到" + p.getTotal() +"条数据";

        return map.outMap("200", result, message);
    }

    /**
     * 插入 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map insert(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "插入";

        if (checkName(movies.getMId(), movies.getMName())) {
            row = 0;
            is = false;
            massage = "名称已存在";
        }
        else {
            row = m.insert(movies);

            List<String> types = types();

            String[]arr = movies.getType().split(",");
            for (int i = 0; i < arr.length; i++) {
                if (!types.contains(arr[i])) {
                    Type t = new Type();
                    t.setT(arr[i]);
                    type.insert(t);
                }
            }
            if (row != 0) {
                is = true;
                massage += "成功";
            }
            else {
                is = false;
                massage += "失败";
            }
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 修改 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map update(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "修改";

        if (checkName(movies.getMId(), movies.getMName())) {
            row = 0;
            is = false;
            massage = "名称已存在";
        }
        else {
            row = m.updateById(movies);

            if (row != 0) {
                is = true;
                massage += "成功";
            }
            else {
                is = false;
                massage += "失败";
            }
        }
        return map.outMap("200", is, row, massage);
    }

    /**
     * 删除 Movies 表数据
     *
     * @param movies {Movies} 传入的数据
     * @return  map {Map} 返回的结果
     */
    public Map delete(Movies movies) {

        boolean is = false;
        int row = 0;
        String massage = "删除";

        row = m.deleteById(movies);

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
     * 查询所有的 type
     *
     * @return types {Arrays} 返回的数据
     */
    public List<String> types() {
        MPJQueryWrapper wrapper = new MPJQueryWrapper<Type>()
                .select("*");
        List list = type.selectMaps(wrapper);
        String[]types = new String[list.size()];
        for (int i = 0; i < types.length; i++) {
            types[i] = list.get(i).toString().split("=")[1].split("}")[0];
        }
        return Arrays.asList(types);
    }

    /**
     * 检查命名是否已存在
     *
     * @param id {Integer} id
     * @param name {String} name
     * @return checkName {Boolean} 检查结果
     */
    private Boolean checkName(Integer id, String name) {
        QueryWrapper wrapper = new QueryWrapper<Movies>()
                .select("*")
                .ne("mId", id)
                .eq("mName", name);
        return m.selectMaps(wrapper).size() != 0;
    }
}
