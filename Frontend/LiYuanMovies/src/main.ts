import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "@/assets/style/common.css";

import ElementPlus from "element-plus";
import zhCn from "element-plus/es/locale/lang/zh-cn.mjs";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(ElementPlus, {
  locale: zhCn,
});

app.mount("#app");
