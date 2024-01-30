<template>
  <div class="container">
    <n-layout :inverted="inverted" content-style="height: 100vh;">
      <n-layout-header bordered>颐和园路</n-layout-header>
      <n-layout has-sider>
        <n-layout-sider bordered content-style="padding: 24px;">
          <div style="height: 100%;">123</div>
        </n-layout-sider>
        <n-layout-content bordered content-style="padding: 24px;">
          平山道
        </n-layout-content>
      </n-layout>
      <n-layout-footer>成府路</n-layout-footer>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const inverted = ref(true);
</script>

<style scoped></style>
