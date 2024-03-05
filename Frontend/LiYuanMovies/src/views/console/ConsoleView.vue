<template>
  <div class="container">
    <el-container>
      <el-aside>
        <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="menu"
          :default-active="_active"
          text-color="#fff"
          router
        >
          <el-menu-item class="menu-item" index="/console/home">
            <img
              class="menu-log"
              src="../../assets/favico.ico"
              alt=""
              srcset=""
            />
          </el-menu-item>

          <el-menu-item>
            <h1>梨园影城后台管理系统</h1>
          </el-menu-item>

          <el-menu-item v-for="(i, key) in menu" :key="key" :index="i.path">
            {{ i.name }}
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="padding: 0">
          <div class="title-header container">
            <h1 class="title" :val="text">{{ text }}</h1>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
// 欢迎使用，梨园影城有后台管理系统
const text = ref("");

const rag = /(\S*)5173/;
const href = window.location.href;
const _href = href.replace(rag, "");
const _active = _href.split("/").length === 3 ? _href : "/console/home";

const menu = [
  { name: "首页", path: "/console/home" },
  { name: "用户管理", path: "/console/user" },
  { name: "电影管理", path: "/console/movie" },
  { name: "影厅管理", path: "/console/room" },
  { name: "订单管理", path: "/console/order" },
];
</script>

<style scoped lang="scss">
@import url("@/assets/style/console.css");
</style>
