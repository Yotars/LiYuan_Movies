<template>
  <div class="centreBox">
    <div class="centre">
      <navber />
      <router-view />
      <back-top />
    </div>
  </div>
</template>

<script setup lang="ts">
import navber from "@/components/NavberComponent.vue";
import backTop from "@/components/BackTopComponent.vue";
</script>
