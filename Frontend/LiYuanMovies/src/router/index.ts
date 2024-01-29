import {
  createRouter,
  createWebHistory,
  type RouteRecordRaw,
} from "vue-router";
import path from './path'

/**
 * 读取 views 中的 config 路径配置文件
 */
const paths = path;

/**
 * 读取 views 中的所有 vue组件
 */
const views = import.meta.glob("../views/**/**.vue");

/**
 * 将两个读取到的配置合并
 * 
 * 以 paths 中的配置为主，如匹配到同路径优先选择 paths 中的配置
 */
const view = Object.assign({}, views, paths);

/**
 * 生成路由配置
 */
const viewsArr = Object.keys(view).map((key) => {
  let name = "";
  let path = "";
  const component = key;

  /**
   * 判断路径指向是否为组件
   * 
   * 是，则将组件路径解析为对应的 组件名 与 路径
   * 否，则将配置中的 组件名 与 路径 填入 name、path
   */
  if (typeof view[key] !== "function") {
    name = view[key].name;
    path = view[key].path;
  } else {
    name = key
      .split("/")[key.split("/").length - 1]
      .split(".")[0]
      .replace("View", "")
      .toLowerCase();
    path = "/" + name;
  }
  return {
    name: name,
    path: path,
    component: import(component),
  };
});

const routes: Array<RouteRecordRaw> = viewsArr;

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});

export default router;
