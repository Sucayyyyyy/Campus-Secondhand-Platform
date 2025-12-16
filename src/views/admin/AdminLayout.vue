<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <h3>管理中心</h3>
      <nav>
        <router-link to="/admin/dashboard" active-class="active">面板概览</router-link>
        <router-link to="/admin/products" active-class="active">商品管理</router-link>
        <router-link to="/admin/categories" active-class="active">分类管理</router-link>
      </nav>
    </aside>
    
    <div class="main-content">
      <header class="header">
        <span class="welcome-msg">您好，{{ authStore.userInfo?.name || '管理员' }}</span>
        <button @click="handleLogout" class="logout-btn">退出登录</button>
      </header>
      
      <main class="page-content">
        <router-view /> 
      </main>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const authStore = useAuthStore();

const handleLogout = () => {
  authStore.logout();
  router.push('/admin/login');
};
</script>

<style scoped>
.admin-layout { /* ... 样式 ... */ }
.sidebar { /* ... 样式 ... */ }
/* ... 其他样式 ... */
</style>