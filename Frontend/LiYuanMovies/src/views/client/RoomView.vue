<template>
  <div class="centreBox">
    <div class="box centre">
      <div class="serach centre">
        <el-input
          v-model="query.mName"
          style="width: 500px"
          placeholder="输入电影名" />
        <el-button type="primary" @click="getRoom()">搜索</el-button>
      </div>

      <div class="centreBox card-box">
        <div v-for="(i, index) in data.room" class="centre card" @click="info(i.mId)" :key="index">
          <img
            style="width: 200px; height: 300px"
            :src="`src/assets/img/${i.mImage}`" />
          <div class="mask">
            <h5>
              {{ i.mName }}
            </h5>
            <p>
              {{ i.Intro.slice(0, 55) + "..." }}
            </p>
            <el-button type="primary" @click="info(i.mId)">查看详情</el-button>
          </div>
        </div>
      </div>

      <div class="centre pagination" v-if="query.total > 10">
        <el-pagination
          v-model:current-page="query.current"
          v-model:page-size="query.size"
          :total="query.total"
          :page-sizes="[10, 20]"
          layout="total, prev, pager, next, sizes"
          @size-change="getRoom()"
          @current-change="getRoom()" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { room } from "@/api/request";
import { reactive, ref } from "vue";
import { Base64 } from "js-base64";
import router from "@/router";

const data = reactive({
  room: [
    {
      rId: "",
      rName: "",
      mId: "",
      start: "",
      end: "",
      rTotal: "",
      rest: "",
      mName: "",
      mImage: "",
      Intro: "",
    },
  ],
});
const query = ref({
  current: 1,
  size: 10,
  pages: 0,
  total: 0,
  mName: "",
  launchDate: "",
  type: "",
});
const getRoom = async () => {
  const { result, message }: any = await room.list(query.value);
  console.log(message);
  data.room.splice(0);
  data.room.push(...result.records);
};
const info = (id: string) => {
  const key = Base64.encode("id");
  const value = Base64.encode(id);
  localStorage.setItem(key, value);
  router.push({ name: "info" });
};
getRoom();
</script>

<style scoped lang="scss">
.centreBox:has(.centre) {
  flex-direction: column;
}
.centreBox:has(.type) {
  margin-top: 20px;
}
.box {
  margin-top: 20px;
  height: 800px;
  width: 1200px;
  background-color: #fff;
  padding: 10px;
  border-radius: 20px;
  filter: drop-shadow(0 0 10px #000000);
  .card-box {
    height: 690px;
    flex-wrap: wrap;
    .card {
      margin-left: auto;
      margin-right: auto;
      margin-bottom: 25px;
      margin-top: 20px;
    }
  }
  .serach {
    width: 600px;
  }
  .pagination {
    width: 500px;
  }
  .card {
    position: relative;
    transition: all 0.3s;
    background-color: #fff;
    filter: drop-shadow(0 0 10px #000000);
    border-radius: 10px;
    height: 300px;
    width: 200px;
    overflow: hidden;
    &:hover {
      transform: scale(1.1);
      z-index: 9;
      cursor: pointer;
      .mask {
        transition: all 0.3s;
        transform: translateY(-107px);
      }
    }
    .mask {
      padding: 5px;
      position: absolute;
      bottom: -107px;
      background-color: #00000090;
      color: #fff;
      width: 200px;
      h5 {
        font-weight: 900;
        width: 200px;
        height: 15px;
        line-height: 15px;
        font-size: 13px;
        margin-bottom: 5px;
      }
      h5 {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      p {
        text-indent: 2em;
        height: 70px;
        font-size: 12px;
      }
      .el-button {
        width: 180px;
      }
    }
  }
}
.type {
  width: 1100px;
  background-color: #fff;
  border-radius: 20px;
  padding: 20px;
  filter: drop-shadow(0 0 10px #000000);
}
</style>
