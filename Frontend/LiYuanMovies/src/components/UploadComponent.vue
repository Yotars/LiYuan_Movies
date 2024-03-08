<template>
  <div>
    <input type="file" name="file" id="file" @change="getFile" />
    <button @click="upload">上传</button>
    <div id="img">
      <img :src="srcImg" alt="等待上传" srcset="" />
    </div>
    {{ base64 }}
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";
const base64 = ref("");
const fileName = ref("");
const srcImg = ref("");

const getFile = function (e: any) {
  const file = e.target.files[0];
  fileName.value = file.name;
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = function () {
    base64.value = reader.result as string;
  };
  reader.onerror = function (error) {
    console.log("Error: ", error);
    return;
  };
};

const upload = function () {
  const owner: string = "yotars";
  const repo: string = "MapBad";
  const path: string = `image/LiYuanMovies/${fileName.value}`;
  const url: string = `https://gitee.com/api/v5/repos/${owner}/${repo}/contents/${path}`;
  const access_token: string = "681dd65a13be448fc119a193c463e7ba";
  
  const message: string = "上传一张图片";
  axios
    .post(url, {
      access_token: access_token,
      message: message,
      content: base64.value.split(",")[1],
    })
    .then(res => {
      alert("上传成功");
      console.log(res);
      srcImg.value = res.data.content.download_url;
      return;
    })
    .catch(err => {
      console.log(err);
    });
};
</script>

<style scoped>
img {
  outline: 1px solid #000000;
  margin-top: 50px;
  width: 500px;
}
</style>
