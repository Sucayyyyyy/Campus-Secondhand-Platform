<template>
  <div class="login-page">
    <div class="login-card">
      
      <h2 class="title">{{ isLoginMode ? '校园二手平台登录' : '管理员注册' }}</h2>

      <form @submit.prevent="isLoginMode ? login() : register()">
        
        <label for="username">用户名:</label>
        <input id="username" v-model="userData.username" required />
        
        <label for="password">密码:</label>
        <input id="password" type="password" v-model="userData.password" required />

        <div v-if="!isLoginMode">
            <label for="confirmPassword">确认密码:</label>
            <input id="confirmPassword" type="password" v-model="userData.confirmPassword" required />
        </div>

        <button type="submit" class="submit-btn" :disabled="isLoading">
            {{ isLoginMode ? '登录' : '注册' }}
        </button>
        
        <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>
      </form>

      <p class="toggle-mode">
        <span @click="toggleMode" class="toggle-link">
          {{ isLoginMode ? '还没有账号？去注册' : '已有账号？去登录' }}
        </span>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth'; // 导入状态管理

const router = useRouter();
const authStore = useAuthStore();

// ===================================================================
// 状态定义
// ===================================================================

// 修复点 1：定义并初始化 userData，这是解决 "reading 'username'" 的关键
const userData = ref({
    username: '',
    password: '',
    confirmPassword: '', 
});

const isLoginMode = ref(true); // true 表示登录，false 表示注册
const isLoading = ref(false);
const errorMsg = ref('');


// ===================================================================
// 函数定义
// ===================================================================

// 修复点 2：将 toggleMode 移到顶层
const toggleMode = () => {
    isLoginMode.value = !isLoginMode.value;
    // 切换模式时清空错误信息和部分表单数据
    errorMsg.value = '';
    userData.value.password = '';
    userData.value.confirmPassword = '';
};

// 修复点 3 & 4：合并 handleLogin 逻辑到 login 函数，并使用 userData
const login = async () => {
    errorMsg.value = '';
    isLoading.value = true;
    
    try {
        // 修复点 4：使用 userData.value 中的属性
        await authStore.login(userData.value.username, userData.value.password);
        
        // 登录成功：跳转到后台管理首页
        router.push('/admin/dashboard'); 
        
    } catch (err) {
        // 登录失败：显示错误信息
        errorMsg.value = '登录失败，请检查用户名或密码是否正确。';
        // 打印后端返回的详细错误（如果 authStore.login 有抛出）
        console.error('登录失败:', err); 
    } finally {
        isLoading.value = false;
    }
};

const register = () => {
    // TODO: 实现注册逻辑
    console.log('执行注册逻辑', userData.value);
    errorMsg.value = '注册功能暂未实现'; // 临时提示
};
</script>

<style scoped>
/* 1. 全局和布局美化 */
.login-page {
    /* 整个页面的背景色：使用柔和的浅灰色 */
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f7f9fb; /* 浅柔和蓝灰色 */
}

/* 2. 登录/注册卡片美化 */
.login-card {
    width: 380px; /* 略微拓宽卡片 */
    padding: 40px; /* 增加内边距 */
    background: white;
    border-radius: 12px; /* 更圆润的边角 */
    /* 现代轻微阴影，增加悬浮感 */
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08); 
    transition: transform 0.3s ease;
}

.title {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
    font-size: 24px;
    font-weight: 600;
}

/* 3. 表单元素和输入框改进 */
label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500; /* 略微减轻粗细 */
    color: #555;
    font-size: 14px;
}

input {
    width: 100%;
    padding: 12px;
    margin-bottom: 20px;
    border: 1px solid #e0e4e8; /* 柔和的边框色 */
    border-radius: 6px;
    box-sizing: border-box;
    font-size: 16px;
    transition: border-color 0.3s, box-shadow 0.3s;
}

input:focus {
    border-color: #42b983; /* 聚焦时使用品牌色 */
    box-shadow: 0 0 0 3px rgba(66, 185, 131, 0.15); /* 聚焦时的光环效果 */
    outline: none;
}

/* 4. 按钮美化 */
.submit-btn {
    width: 100%;
    padding: 12px;
    background-color: #42b983; /* 绿色品牌色 */
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 600;
    margin-top: 15px;
    transition: background-color 0.3s, transform 0.1s;
}

.submit-btn:hover {
    background-color: #369c70; /* 鼠标悬停时略微变暗 */
}

.submit-btn:active {
    transform: translateY(1px); /* 点击时的下沉效果 */
}

.submit-btn:disabled {
    background-color: #a0d9b4;
    cursor: not-allowed;
}

/* 5. 错误信息和切换链接 */
.error-msg {
    color: #e54a4a; /* 使用更明显的错误红色 */
    text-align: center;
    margin-top: 15px;
    font-size: 14px;
}

.toggle-mode {
    text-align: center;
    margin-top: 25px;
    font-size: 14px;
}

.toggle-link {
    color: #42b983;
    cursor: pointer;
    text-decoration: none; /* 移除默认下划线 */
    font-weight: 500;
}

.toggle-link:hover {
    text-decoration: underline; /* 鼠标悬停时添加下划线 */
}
</style>