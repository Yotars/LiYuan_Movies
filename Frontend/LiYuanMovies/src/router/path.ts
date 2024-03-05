interface path {
  [key: string]: {
    name?: string;
    path?: string;
    redirect?: string;
  };
}
/**
 * 这里添加自定义路由 name 与 path，例：
 * [组件路径: string]: {
 *   name: '组件名' //  没有 name 参数，默认从组件路径中解析
 *   path: '路径名' //  没有 path 参数，默认为 '/' + 组件名
 *   redirect: '默认显示页面' //  没有 redirect 参数，默认为 '/'
 * }
 * 支持添加自定义组件，但路径一定要写对
 */
export const path: path = {
  "../views/client/IndexView.vue": {
    name: "index",
    path: "/",
    redirect: "/home",
  },
  "../views/console/ConsoleView.vue": {
    name: "console",
    path: "/",
    redirect: "/console/home",
  },
  "../views/client/UesrView.vue": {
    name: "user",
    path: "/user",
  },
};
