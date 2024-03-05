<template>
  <ul class="nav centreBox">
    <li
      class="centre tag"
      :class="color === index ? 'color1' : 'color0'"
      @click="button(index)"
      v-for="(i, index) in links"
      :key="index">
      <router-link :to="i.href">{{ i.name }}</router-link>
    </li>
    <li
      class="centre tag"
      @click="button(3)"
      :class="color === 3 ? 'color1' : 'color0'">
      <router-link v-if="!data.info.nickname" to="/login">
        登录\注册
      </router-link>
      <el-dropdown v-else class="tag">
        <router-link to="/user">
          <el-avatar
            :size="25"
            style="vertical-align: -6px"
            :src="`src/assets/head/${data.info.head}`"
            :src-set="`src/assets/head/${data.info.head}`" />
          {{ data.info.nickname }}
        </router-link>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <router-link to="/user"> 用户信息 </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/order"> 订单详情 </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/write"> 退出登陆 </router-link>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </li>
  </ul>
</template>

<script setup lang="ts">
import { info, user } from "@/api/request";
import { reactive, ref } from "vue";

const links: {
  name: string;
  href: string;
}[] = [
  { name: "首页", href: "/home" },
  { name: "电影", href: "/movies" },
  { name: "正在热映", href: "/room" },
];

const data = reactive({
  info: {
    uId: 0,
    nickname: "",
    head: "",
    sex: 0,
    birth: "",
    address: "",
    phone: "",
    email: "",
  },
  form: {
    uId: 0,
    nickname: "",
    head: "",
    sex: 0,
    birth: "",
    address: "",
    phone: "",
    email: "",
  },
});

const getInfo = async () => {
  const {
    result: { user: u },
  }: any = await user.isAdmin({
    token: localStorage.getItem("token"),
  });
  const { result }: any = await info.list({ uId: u.uid });
  data.info = Object.assign({}, data.info, result.records);
  data.form = Object.assign({}, data.form, result.records);
};

const color = ref(0);
const button = (i: number) => {
  color.value = i;
};
const t = setInterval(() => {
  if (localStorage.getItem("token")) {
    clearInterval(t);
    getInfo();
  }
});
</script>

<style lang="scss" scoped>
.nav {
  height: 50px;
  width: 900px;
  margin: auto;
  background-color: #fff;
  border-radius: 0 0 20px 20px;
  overflow: hidden;
  .tag {
    height: 100%;
    width: 100%;
    line-height: 50px;
    text-align: center;
    a {
      display: block;
      height: 100%;
      width: 100%;
      &:hover {
        color: white;
      }
    }
    &:hover {
      transition: all 0.5s;
      background-color: #f1939c;
    }
  }
}

.color0 {
  background-color: #fff;
}
.color1 {
  background-color: #ce5777;
  a {
    color: #fff;
  }
}
</style>
