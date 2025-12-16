<template>
  <div id="app-wrapper">
    <router-view /> 
  </div>
</template>

<script setup>
// 保持简洁，不需要导入任何东西
</script>

<style>
/* 可以在这里放一些全局的基础样式 */
body {
  margin: 0;
  background-color: #f7f7f7; /* 设置一个浅灰色背景，让页面看起来更清晰 */
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
}
#app-wrapper {
  /* 确保根容器宽度占满 */
  min-height: 100vh;
}
</style>