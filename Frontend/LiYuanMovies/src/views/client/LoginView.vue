<template>
  <div class="centreBox box">
    <el-form class="centre card" label-width="27%">
      <h1>{{ title }}</h1>
      <el-form-item label="用户名">
        <el-input
          style="width: 250px"
          type="text"
          v-model="data.login.username"
          name=""
          id="" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          style="width: 250px"
          type="password"
          v-model="data.login.password"
          name=""
          id="" />
      </el-form-item>
      <el-form-item v-if="title === 'signup'" class="centreBox" label-width="0">
        <el-button class="centre" @click="signup">注册</el-button>
        <el-button class="centre" @click="button('login')">登陆 ></el-button>
      </el-form-item>
      <el-form-item v-else class="centreBox" label-width="0">
        <el-button class="centre" @click="login">登陆</el-button>
        <el-button class="centre" @click="button('signup')">注册 ></el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { user } from "@/api/request";
import router from "@/router";
import { ElMessage } from "element-plus";
import { reactive, ref } from "vue";

const data = reactive({
  login: {
    username: "",
    password: "",
  },
  result: {
    token: "",
    is: false,
  },
});

const title = ref("login");
const button = (option: string) => {
  const options: any = {
    login: () => {
      title.value = "login";
    },
    signup: () => {
      title.value = "signup";
    },
  };
  options[option]();
};

const login = async () => {
  const { result, message }: any = await user.login(data.login);
  data.result = { ...result };
  localStorage.setItem("token", data.result.token);
  Object.freeze(data.result);
  if (result.is) {
    router.push("/");
  }
  ElMessage({
    message: message,
    type: "success",
  });
};

const signup = async () => {
  const { result, message }: any = await user.insert(data.login);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
};
</script>

<style scoped lang="scss">
.box {
  background-color: #fff;
  margin: 10px;
  border-radius: 20px;
  height: 800px;
  .card {
    width: 500px;
    padding: 20px;
    border-radius: 20px;
    background-color: #fff;
    filter: drop-shadow(0 0 10px #00000069);
    h1 {
      text-align: center;
      margin: 20px;
    }
  }
}
.centreBox {
  .centre {
    margin: auto;
  }
}
</style>
