<template>
  <div class="box">
    <div class="centreBox content">
      <div ref="img" class="centre">
        <img :src="`src/assets/img/${data.movies.mImage}`" alt="" />
      </div>
      <div ref="info" class="centre">
        <h1>{{ data.movies.mName }}</h1>
        <el-descriptions column="1">
          <el-descriptions-item label="导演">{{
            data.movies.director
          }}</el-descriptions-item>
          <el-descriptions-item label="主演">{{
            data.movies.actors
          }}</el-descriptions-item>
          <el-descriptions-item label="类型">
            <el-tag
              v-for="i in data.movies.type.split(' / ')"
              :key="i"
              style="margin-right: 6px">
              {{ i }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="地区">
            <el-tag
              v-for="i in data.movies.area.split(' / ')"
              :key="i"
              style="margin-right: 6px">
              {{ i }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="语言">
            {{ data.movies.language }}
          </el-descriptions-item>
          <el-descriptions-item label="上映时间">
            {{ data.movies.launchDate }}
          </el-descriptions-item>
          <el-descriptions-item label="片长">
            {{ data.movies.filmLength }}
          </el-descriptions-item>
          <el-descriptions-item label="IMDb">
            {{ data.movies.IMDb }}
          </el-descriptions-item>
          <el-descriptions-item>
            <el-button
              type="primary"
              @click="jump(data.movies.mName)"
              v-if="isRoom">
              在线播放
            </el-button>
            <el-button type="primary" @click="open()" v-else
              >订购影票</el-button
            >
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <div ref="intro" class="intro">
      <p class="text" size="large">{{ data.movies.Intro }}</p>
    </div>
  </div>
  <el-dialog
    v-model="dialogVisible"
    style="padding: 5px"
    title="选择座位"
    width="850">
    <div class="cardBox centreBox">
      <div
        class="card centre"
        :class="[['color1', 'color0'][isColor[+i.sId]]]"
        v-for="(i, index) in data.seat"
        :key="index"
        @click="button(i.sId, !i.status)">
        {{ i.sId }}
      </div>
    </div>
    <div class="fClear">
      <div class="fl">
        已选择座位：
        <el-tag
          style="margin-right: 6px"
          v-for="(i, index) in data.form.seat"
          :key="index">
          {{ i }}
        </el-tag>
      </div>
      <div class="fr">
        <el-button @click="createOrder"> 确认 </el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { room, movies, seat, user, order } from "@/api/request";
import router from "@/router";
import { Base64 } from "js-base64";
import { getCurrentInstance, onMounted, reactive, ref } from "vue";

const key = Base64.encode("id");
const value: any = localStorage.getItem(key);
value === null && router.push({ name: "home" });
const id = Base64.decode(value);
const isRoom = ref(false);

const data = reactive({
  movies: {
    mId: 0,
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
  room: {
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
  seat: [
    {
      sId: "",
      rId: "",
      status: "",
      r: "",
      l: "",
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
    mId: id,
  },
  order: {
    oId: "",
    uId: "",
    oNumber: "",
    oDetails: "",
    oMessage: "",
    oDate: "",
    oStatus: "",
  },
  form: {
    uId: "",
    seat: new Array(),
    oDetails: "",
  },
});

const getMovies = async () => {
  const { result }: any = await movies.list(data.query);
  data.movies = Object.assign({}, result.records[0]);
};

const getRoom = async () => {
  const { result }: any = await room.list(data.query);
  data.movies = Object.assign({}, result.records[0]);
  data.query = Object.assign(data.query, result.pagation);
  data.query = Object.assign(data.query, result.records[0]);
  isRoom.value = !(result.records.length > 0);
};

const getSeat = async () => {
  const { result }: any = await seat.list(data.query);
  data.seat.splice(0);
  data.seat.push(...result.records);
};

const jump = (name: string) => {
  window.open(`https://v.qq.com/x/search/?q=${name}`, "_blank");
};

const dialogVisible = ref(false);
const open = () => {
  dialogVisible.value = true;
};

const isColor = ref([0]);

const button = (id: string, is: boolean) => {
  const index = data.form.seat.indexOf(id);
  if (is && index === -1) {
    data.form.seat.push(id);
    isColor.value[+id] = 1;
  } else if (is && index !== -1) {
    isColor.value[+id] = 0;
    data.form.seat.splice(index, 1);
  }
};

const createOrder = async () => {
  const {
    result: { user: u },
  }: any = await user.isAdmin({
    token: localStorage.getItem("token"),
  });
  data.form.uId = u.uId;
  data.form.oDetails = data.form.seat.toString();
  const { result, message }: any = order.insert(data.form);
  data.order = Object.assign({}, result.order);
  if (result.is) {
    console.log(message);
  }
};

onMounted(async () => {
  const instance = getCurrentInstance();
  const dom = {
    img: instance?.refs.img as HTMLDivElement,
    info: instance?.refs.info as HTMLDivElement,
    intro: instance?.refs.intro as HTMLDivElement,
  };
  await getMovies();
  await getRoom();
  await getSeat();

  for (let i = 0; i < data.seat.length; i++) {
    isColor.value.push(+data.seat[i].status);
  }
  const width = dom.img.offsetWidth + dom.info.offsetWidth;
  dom.intro.style.width = width + 50 + "px";
});
</script>

<style scoped lang="scss">
.box {
  margin-top: 50px;
  padding: 20px;
  background-color: #fff;
  .intro {
    width: 100px;
    margin: 0 auto;
    .text {
      text-indent: 2em;
    }
  }
}
.centreBox {
  justify-content: center;
  .centre {
    margin: 20px;
    img {
      width: 250px;
    }
  }
}
.cardBox {
  &.centreBox {
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    width: 800px;
  }
  .card {
    width: 50px;
    height: 50px;
    border-radius: 10px;
    transition: all 0.5s;
    cursor: pointer;
    text-align: center;
    line-height: 50px;
    font-weight: 900;
    color: white;
    caret-color: transparent;
    &:hover {
      background-color: #a1a1a1;
    }
  }
  .color0 {
    background-color: aqua !important;
  }
  .color1 {
    background-color: green;
  }
}
</style>
