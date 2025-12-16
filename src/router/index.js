import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [],
})

export default router


// 导入刚刚创建的 Home 组件
import Home from '@/views/front/Home.vue' 

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home // 确保路径正确指向您的 Home.vue 文件
  },
  // ... 其他路由
];

// ... 其他代码