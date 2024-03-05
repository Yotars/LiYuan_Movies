/**
 * 定义url对象中的参数类型
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
 * 自定义接口默认POST请求
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
  /**
   * 例：
   * 后端有一个user接口
   * 它基础（增删改查）中只有 删除(delete)和修改(updata)
   * 后端还添加了自定义的登陆(login)与注册(sigup)注册功能
   * 于是用 api 把 login，sigup 加入生成队列中
   * 用 not 把 （增删改查）查找(list)与添加(insert) 从生成队列中排除
   */
  // user: {
  //   api: ["login", "sigup"],
  //   not: ["list", "insert"],
  // },
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


/**
 * 设置后端增删改查的接口命名：
 * 请按照 GET POST PUT DELETE 填入，可以使用对应的命名对后端发出请求
 */
export const apiName = ["list", "insert", "update", "delete"];

/**
 * 根据 urls 进行二次封装
 * 并生成 requset.ts保存到本地
 */
export const generateRequset = () => {
  let fileData = `
import api from "./api";

/**
 * 封装返回结果
 *
 * @param res 请求结果
 * @returns 返回封装结果
 */
const process = (res: any) => {
  return new Promise((resolve) => {
    const data = res.then(
      (res: { message: string; result: object }) => {
        console.log(res.message);
        return res;
      }
    );
    return resolve(data);
  });
};\n`;

  for (const key in urls) {
    const url: any = urls[key];
    const arr = [...apiName];
    if (typeof url === "object" && typeof url.not !== "undefined") {
      for (let i = 0; i < url.not.length; i++) {
        const index = arr.indexOf(url.not[i]);
        arr.splice(index, 1);
      }
    } else if (typeof url === "object" && typeof url.api !== "undefined") {
      arr.push(...url.api);
    }
    fileData += `
/**
 * ${key}请求函数
 */
export const ${key} = {`;
    for (let i = 0; i < arr.length; i++) {
      fileData += `
  ${arr[i]}: (item: {}) => {
    return process(api.user.${arr[i]}(item));
  },`;
    }
    fileData += `
};\n`;
  }
  // 创建文件
  const file = new File([fileData], "request.ts", {
    type: "text/plain",
  });
  // 保存文件
  const link = document.createElement("a");
  link.href = URL.createObjectURL(file);
  link.download = "request.ts";
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  URL.revokeObjectURL(link.href);
};
