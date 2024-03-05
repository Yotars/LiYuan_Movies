<template>
  <div class="card-box">
    <div v-for="i in 10" :key="i">
      <div class="card">
        {{ i }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.card-box {
  width: 700px;
  height: 100px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  div {
    position: relative;
    
    .card {
      position: absolute;
      background-color: aliceblue;
      width: 100px;
      height: 100px;
    }
  }
}
</style>
