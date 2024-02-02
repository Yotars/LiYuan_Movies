<template>
  <div v-if="isConsole">
    <console />
  </div>
  <div v-else>
    <RouterView />
  </div>
</template>

<script setup lang="ts">
import console from "./views/console/ConsoleView.vue";
const link = window.location.href.split("/")[3];
const isConsole = link === "console";
</script>

<style scoped></style>
