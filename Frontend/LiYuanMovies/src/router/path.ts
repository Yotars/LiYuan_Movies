interface path {
  [key: string]: {
    name?: string;
    path?: string;
  };
}
/**
 * 这里添加自定义路由 name 与 path，例：
 * [组件路径: string]: {
 *   name: '组件名',
 *   path: '路径名' //  没有path参数，默认为 '/' + 组件名
 * }
 * 支持添加自定义组件，但路径一定要写对
 */
export const path: path = {
  "../views/console/ConsoleView.vue": {
    name: "console",
    path: "/",
  },
  "../views/console/HomeView.vue": {
    name: "consoleHome",
    path: "/home",
  },
  "../views/client/HomeView.vue": {
    name: "home",
    path: "/",
  },
};
