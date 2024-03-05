import api from "./api";

/**
 * 生成的二次封装的接口可以直接调用
 * 比如：
 * user.login(传入对象)
 */

/**
 * 封装返回结果
 *
 * @param res 请求结果
 * @returns 返回封装结果
 */
const process = (res: any) => {
  return new Promise(resolve => {
    const data = res.then((res: any) => {
      return res;
    });
    return resolve(data);
  });
};

/**
 * user请求函数
 */
export const user = {
  list: (item: {}) => {
    return process(api.user.list(item));
  },
  insert: (item: {}) => {
    return process(api.user.insert(item));
  },
  update: (item: {}) => {
    return process(api.user.update(item));
  },
  delete: (item: {}) => {
    return process(api.user.delete(item));
  },
  login: (item: {}) => {
    return process(api.user.login(item));
  },
  isAdmin: (item: {}) => {
    return process(api.user.isAdmin(item));
  },
};

/**
 * info请求函数
 */
export const info = {
  list: (item: {}) => {
    return process(api.info.list(item));
  },
  update: (item: {}) => {
    return process(api.info.update(item));
  },
  delete: (item: {}) => {
    return process(api.info.delete(item));
  },
};

/**
 * order请求函数
 */
export const order = {
  list: (item: {}) => {
    return process(api.order.list(item));
  },
  update: (item: {}) => {
    return process(api.order.update(item));
  },
  delete: (item: {}) => {
    return process(api.order.delete(item));
  },
  insert: (item: {}) => {
    return process(api.order.insert(item));
  },
  cancel: (item: {}) => {
    return process(api.order.cancel(item));
  },
  done: (item: {}) => {
    return process(api.order.done(item));
  },
};

/**
 * movies请求函数
 */
export const movies = {
  list: (item: {}) => {
    return process(api.movies.list(item));
  },
  update: (item: {}) => {
    return process(api.movies.update(item));
  },
  delete: (item: {}) => {
    return process(api.movies.delete(item));
  },
  insert: (item: {}) => {
    return process(api.movies.insert(item));
  },
};

/**
 * room请求函数
 */
export const room = {
  list: (item: {}) => {
    return process(api.room.list(item));
  },
  update: (item: {}) => {
    return process(api.room.update(item));
  },
  delete: (item: {}) => {
    return process(api.room.delete(item));
  },
  insert: (item: {}) => {
    return process(api.room.insert(item));
  },
};

/**
 * seat请求函数
 */
export const seat = {
  list: (item: {}) => {
    return process(api.seat.list(item));
  },
  update: (item: {}) => {
    return process(api.seat.update(item));
  },
  delete: (item: {}) => {
    return process(api.seat.delete(item));
  },
  insert: (item: {}) => {
    return process(api.seat.insert(item));
  },
};
