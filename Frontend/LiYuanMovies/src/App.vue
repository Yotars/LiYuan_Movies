<template>
  <div v-if="isConsole">
    <console />
  </div>
  <div v-else>
    <index />
  </div>
</template>

<script setup lang="ts">
import console from "./views/console/ConsoleView.vue";
import index from "./views/client/IndexView.vue";
import { ref } from "vue";

const link = ref(window.location.href.indexOf("console"));
const isConsole = ref(link.value !== -1);
</script>

<style scoped></style>
