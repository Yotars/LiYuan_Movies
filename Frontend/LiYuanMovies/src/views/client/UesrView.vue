<template>
  <div class="container">
    <div class="fClear">
      <div class="fr">
        <div v-if="!f">
          <el-button type="warning" @click="button('open')">编辑</el-button>
        </div>
        <div v-else>
          <el-button type="primary" @click="changeInfo">完成</el-button>
          <el-button type="info" @click="button('close')">取消</el-button>
        </div>
      </div>
    </div>
    <div v-if="!f">
      <el-avatar
        :size="100"
        :src="`src/assets/head/${data.info.head}`"
        :src-set="`src/assets/head/${data.info.head}`" />
      <el-descriptions size="large" column="2">
        <el-descriptions-item label="昵称">{{
          data.info.nickname
        }}</el-descriptions-item>
        <el-descriptions-item label="性别">
          {{ ["女", "男"][data.info.sex] }}
        </el-descriptions-item>
        <el-descriptions-item label="出生年月">{{
          data.info.birth
        }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">
          {{ data.info.email || "空" }}
        </el-descriptions-item>
        <el-descriptions-item label="地址">
          {{ data.info.email || "空" }}
        </el-descriptions-item>
        <el-descriptions-item label="邮箱">
          {{ data.info.email || "空" }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <div v-else>
      <el-upload class="avatar-uploader" :show-file-list="false">
        <img
          v-if="data.form.head"
          style="width: 100px; border-radius: 50%"
          :src="`src/assets/head/${data.form.head}`"
          class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
      <el-descriptions size="large" column="2">
        <el-descriptions-item label="昵称">
          <el-input
            style="width: 250px"
            v-model="data.form.nickname"
            placeholder="请输入用户名" />
        </el-descriptions-item>

        <el-descriptions-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio-button label="1" size="large" border>男</el-radio-button>
            <el-radio-button label="0" size="large" border>女</el-radio-button>
          </el-radio-group>
        </el-descriptions-item>

        <el-descriptions-item label="出生年月">
          <el-date-picker
            style="width: 222px"
            v-model="data.form.birth"
            type="date"
            format="YYYY年 MM月 DD日"
            value-format="YYYY-MM-DD" />
        </el-descriptions-item>

        <el-descriptions-item label="联系电话">
          <el-input style="width: 222px" v-model="data.form.phone" />
        </el-descriptions-item>

        <el-descriptions-item label="地址">
          <el-input style="width: 250px" v-model="data.form.address" />
        </el-descriptions-item>

        <el-descriptions-item label="邮箱">
          <el-input style="width: 250px" v-model="data.form.email" />
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<script setup lang="ts">
import { info, user } from "@/api/request";
import { reactive, ref } from "vue";

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

const f = ref(false);
const changeInfo = async () => {
  const { result, message }: any = await info.update(data.form);

  if (result.is) {
    console.log(message);
  }
  f.value = false;
  getInfo();
};
const button = (option: string) => {
  const options: any = {
    open: () => {
      f.value = true;
    },
    close: () => {
      f.value = false;
    },
  };
  options[option]();
  getInfo();
};
getInfo();
</script>

<style scoped lang="scss">
.container {
  padding: 50px;
  padding: 50px;
  background-color: #fff;
  margin-top: 20px;
}
</style>
