<template>
  <div class="login-container">
    <div class="login-box">
      <h2>管理员登录</h2>
      
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="username">用户名:</label>
          <input type="text" id="username" v-model="username" required>
        </div>

        <div class="input-group">
          <label for="password">密码:</label>
          <input type="password" id="password" v-model="password" required>
        </div>

        <button type="submit" :disabled="isLoading">
          {{ isLoading ? '登录中...' : '登 录' }}
        </button>

        <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth'; // 导入状态管理

const router = useRouter();
const authStore = useAuthStore();

const username = ref('admin'); // 默认值
const password = ref('123456');
const isLoading = ref(false);
const errorMsg = ref('');

const handleLogin = async () => {
  errorMsg.value = '';
  isLoading.value = true;
  
  try {
    // 调用 Pinia Store 中的登录 Action
    await authStore.login(username.value, password.value);
    
    // 登录成功：跳转到后台管理首页 (例如 /admin/dashboard)
    router.push('/admin/dashboard'); 
  } catch (err) {
    // 登录失败：显示错误信息
    errorMsg.value = '登录失败，请检查用户名或密码是否正确。';
    console.error('登录失败:', err);
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
/* 简单的居中和美化 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f4f7f6;
}
.login-box {
  width: 350px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}
h2 {
  text-align: center;
  margin-bottom: 25px;
  color: #333;
}
.input-group {
  margin-bottom: 15px;
}
.input-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}
.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
  transition: background-color 0.3s;
}
button:disabled {
  background-color: #a0d9b4;
  cursor: not-allowed;
}
.error-msg {
  color: red;
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
}
</style>