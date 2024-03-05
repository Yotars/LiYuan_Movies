import axios from "axios";
import { apiName, urls } from "./url";

const baseURL: string = "/api";

interface apis {
  [key: string]: {
    [key: string]: any;
  };
}

/**
 * 根据读取到的配置生成请求函数
 * @param key 对应配置的 key
 * @returns 生成的请求函数
 */
const requeset = (key: string) => {
  /**
   * 读取配置
   */
  const url = urls[key];

  /**
   * 请求配置
   * @param url 请求路径
   * @param item 请求体
   * @param method 请求方法
   */
  const config = (
    url: string,
    item: { [key: string]: string },
    method: string = "POST"
  ) => {
    return {
      url: url,
      method: method,
      params: item,
      baseURL: baseURL,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    };
  };

  /**
   * 默认的 apis
   */
  const method = ["GET", "POST", "PUT", "DELETE"];
  const api = apiName;

  /**
   * 如果配置中有 not 则删除 apis 中对应的 api
   * 如果配置中有 api 则 apis 中添加对应的 api
   */
  if (typeof url === "object" && typeof url.not !== "undefined") {
    for (let i = 0; i < url.not.length; i++) {
      const index = api.indexOf(url.not[i]);
      api.splice(index, 1);
      method.splice(index, 1);
    }
  } else if (typeof url === "object" && typeof url.api !== "undefined") {
    api.push(...url.api);
  }

  /**
   * 根据最后生成的 api 生成请求
   */
  const apis: apis = {};
  for (let i = 0; i < api.length; i++) {
    let url = "/" + key;
    if (method[i] === undefined) {
      url = url + "/" + api[i];
    }
    apis[api[i]] = (item: { [key: string]: string }) => {
      return new Promise(resolve => {
        const res = axios
          .request(config(url, item, method[i]))
          .then((res: { data: object }) => {
            return res.data;
          });
        return resolve(res);
      });
    };
  }
  return apis;
};

/**
 * 根据配置文件 urls 生成请求接口
 */
const apis: apis = {};
for (const key in urls) {
  apis[key] = requeset(key);
}

export default apis;
