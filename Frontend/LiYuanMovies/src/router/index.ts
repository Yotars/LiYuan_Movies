import {
  createRouter,
  createWebHistory,
  type RouteRecordRaw,
} from "vue-router";
import { path } from "./path";

/**
 * 读取 views 中的所有 vue组件
 */
const views = import.meta.glob("../views/**/**.vue");

/**
 * 将两个读取到的配置合并
 *
 * 以 paths 中的配置为主，如匹配到同路径优先选择 paths 中的配置
 */
const view = Object.assign({}, views, path);

/**
 * 获取所有的 import 模块
 */
const modules = import.meta.glob("../**/**/**.vue");

/**
 * 生成路由配置
 */
const routes: Array<RouteRecordRaw> = Object.keys(view).map((key: string) => {
  let name = "";
  let path = "";
  const component = key;
  const keyArr = key.split("/");
  const pathName = keyArr[keyArr.length - 1]
    .toLowerCase()
    .replace("view.vue", "")
    .replace("console.vue", "");

  /**
   * 判断路径指向是否为组件
   *
   * 是，则将组件路径解析为对应的 组件名 与 路径
   * 否，则将配置中的 组件名 与 路径 填入 name、path
   */
  if (typeof view[key] !== "function") {
    name = view[key].name || pathName;
    path = view[key].path || "/" + (view[key].name || pathName);
  } else {
    name = pathName;
    path = "/" + name;
  }
  /**
   * 判断页面路径是否为控制面板
   */
  if (keyArr.indexOf("console") !== -1) {
    path = "/console" + path;
    name = name + "Console";
  }

  const router:
    | {
        name: string;
        path: string;
        component: any;
        redirect?: string;
      }
    | any = {
    name: name,
    path: path,
    component: modules[component],
  };

  if (view[key].redirect) {
    router.redirect = view[key].redirect;
  }
  return router;
});

export default createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});
