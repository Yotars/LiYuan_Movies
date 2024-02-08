/**
 * 定义url对象中的参数
 */
interface url {
  [key: string]:
    | {
        api?: string[];
        not?: string[];
      }
    | string;
}
/**
 *
 * 定义api接口的url
 * api 根据 url 生成对应的 接口 如：GET、POST、PUT、DELETE请求等等。
 *
 * 输入格式：
 *
 * '接口名': {
 *   api: ['自定义接口名'],   // 可选参数，作用：将一些自定义的接口生成到 api 中
 *   not: ['默认接口名']      // 可选参数，作用：过滤默认生成的请求，如：GET、POST、PUT、DELETE
 * },
 *
 * 或者：
 *
 * '接口名':'接口名'          // 默认生成 GET、POST、PUT、DELETE请求。
 */
export const urls: url = {
  user: {
    api: ["login", "isAdmin"],
  },
  info: {
    not: ["insert"],
  },
  order: {
    api: ["cancel", "done"],
  },
  movies: "movies",
  room: "room",
  seat: "seat",
};
