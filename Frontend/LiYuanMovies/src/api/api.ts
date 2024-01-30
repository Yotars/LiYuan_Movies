import axios from "axios";
import { url } from "./url";

const baseURL: string = "/api";

interface urls {
  [key: string]:
    | {
        api?: string[];
        not?: string[];
      }
    | string;
}
interface apis {
  [key: string]: {
    [key: string]: any;
  };
}

const urls: urls = url;

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
  const api = ["list", "insert", "change", "delete"];

  /**
   * 如果配置中有 not 则删除 apis 中对应的 api
   * 如果配置中有 api 则 apis 中添加对应的 api
   */
  if (typeof url === "object" && typeof url.not !== "undefined") {
    const index = [];
    for (let i = 0; i < url.not.length; i++) {
      index.push(api.indexOf(url.not[i]));
    }
    for (let i = 0; i < index.length; i++) {
      api.splice(index[i], 1);
      method.splice(index[i], 1);
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
    apis[api[i]] = async (item: { [key: string]: string }) => {
      const res = await axios.request(config(url, item, method[i]));
      if (res.status === 200) return res.data;
      else "error";
    };
  }
  return apis;
};

export const api: apis[] = Object.keys(urls).map((key: string) => requeset(key));
