<template>
  <div class="centreBox">
    <div class="box hot centre">
      <h3 class="title fClear">
        正在热映
        <span class="fr">
          <router-link to="/room"> 查看更多> </router-link>
        </span>
      </h3>

      <div class="centreBox card-box">
        <div
          v-for="(i, index) in data.room"
          class="centre card"
          @click="info(i.mId)"
          :key="index">
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
    </div>
    <div class="box online centre">
      <h3 class="title fClear">
        在线播放
        <span class="fr">
          <router-link to="/movies"> 查看更多> </router-link>
        </span>
      </h3>
      <div class="centreBox card-box">
        <div
          v-for="(i, index) in data.movies"
          class="centre card"
          @click="info(i.mId)"
          :key="index">
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
    </div>
  </div>
</template>

<script setup lang="ts">
import { room, movies } from "@/api/request";
import router from "@/router";
import { reactive } from "vue";
import { Base64 } from "js-base64";

const data = reactive({
  movies: [
    {
      mId: "",
      mName: "",
      mImage: "",
      director: 1,
      actors: "",
      type: "",
      area: "",
      language: "",
      launchDate: "",
      filmLength: "",
      IMDb: "",
      Intro: "",
    },
  ],
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
  query: {
    current: 1,
    size: 10,
    pages: 0,
    total: 0,
    mName: "",
    launchDate: "",
    type: "",
    rId: "",
  },
});

const getMovies = async () => {
  const { result, message }: any = await movies.list(data.query);
  console.log(message);
  data.query = Object.assign(data.query, result.pagination);
  data.movies.splice(0);
  data.movies.push(...result.records);
};
getMovies();

const getRoom = async () => {
  const { result, message }: any = await room.list(data.query);
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

<style lang="scss" scoped>
.centreBox:has(.box) {
  flex-direction: column;
}
.box {
  width: 1200px;
  background-color: #fff;
  padding: 20px;
  margin-top: 20px;
  border-radius: 20px;
  filter: drop-shadow(0 0 10px #000000);
  &.hot {
    width: 50vw;
    min-width: 700px;
  }
  &.online {
    .card-box {
      height: 100%;
      flex-wrap: wrap;
      .card {
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 25px;
      }
    }
  }
  .title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  .card-box {
    .card {
      position: relative;
      transition: all 0.3s;
      background-color: #fff;
      filter: drop-shadow(0 0 10px #000000);
      border-radius: 10px;
      height: 300px;
      width: 200px;
      margin-bottom: 25px;
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
}
</style>
