<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="sidebar">
      <div class="logo">校园二手管理</div>
      <el-menu
        default-active="/admin/dashboard"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF"
        router 
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><Menu /></el-icon>
          <span>面板概览</span>
        </el-menu-item>
        <el-menu-item index="/admin/products">
          <el-icon><Goods /></el-icon>
          <span>商品管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/add-product">
          <el-icon><Plus /></el-icon>
          <span>发布商品</span>
        </el-menu-item>
        <el-menu-item index="/admin/categories">
          <el-icon><Operation /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item>后台管理</el-breadcrumb-item>
            <el-breadcrumb-item>当前页面</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <span class="user-name">您好，{{ authStore.userInfo?.username || '管理员' }}</span>
          <el-button type="danger" link @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>

      <el-main class="main-body">
        <div class="app-container">
          <router-view /> </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
// 导入图标
import { Menu, Goods, Plus, Operation } from '@element-plus/icons-vue';

const router = useRouter();
const authStore = useAuthStore();

const handleLogout = () => {
  authStore.logout();
  router.push('/admin/login');
};
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.sidebar {
  background-color: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-weight: bold;
  font-size: 18px;
  background-color: #2b2f3a;
}
.header {
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #dcdfe6;
}
.main-body {
  background-color: #f0f2f5;
  padding: 20px;
}
.app-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  min-height: calc(100vh - 120px);
}
.user-name {
  margin-right: 20px;
  font-size: 14px;
  color: #606266;
}
</style>