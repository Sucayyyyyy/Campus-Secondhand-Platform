import { defineStore } from 'pinia';
import { userLogin, getUserProfile } from '@/api/user'; // 导入API

// 假设我们使用localStorage来保持登录状态
const TOKEN_KEY = 'campus_user_token';
const USER_INFO_KEY = 'campus_user_info';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem(TOKEN_KEY) || null, // 从本地存储加载Token
        userInfo: JSON.parse(localStorage.getItem(USER_INFO_KEY)) || null, // 从本地存储加载用户信息
        isLoggedIn: !!localStorage.getItem(TOKEN_KEY), // 根据Token判断是否登录
    }),
    
    actions: {
        /**
         * 处理用户登录逻辑
         * @param {string} username 
         * @param {string} password
         */
        async login(username, password) {
            try {
                // 调用后端登录接口
                const response = await userLogin({ username, password });
                
                // 【重要】根据后端实际返回结构调整
                // 假设后端返回 { token: '...', user: { id: 1, name: '管理员' } }
                const { token, user } = response.data; 

                // 存储状态到 Store 和本地存储
                this.token = token;
                this.userInfo = user;
                this.isLoggedIn = true;
                
                localStorage.setItem(TOKEN_KEY, token);
                localStorage.setItem(USER_INFO_KEY, JSON.stringify(user));
                
                return true; // 登录成功
            } catch (error) {
                this.logout(); // 登录失败时清除状态
                console.error('登录失败:', error);
                throw error; // 抛出错误给组件处理
            }
        },

        // 登出操作
        logout() {
            this.token = null;
            this.userInfo = null;
            this.isLoggedIn = false;
            localStorage.removeItem(TOKEN_KEY);
            localStorage.removeItem(USER_INFO_KEY);
        },

        // 获取用户详情（待实现接口）
        async fetchProfile() {
            if (!this.token) return;
            try {
                const response = await getUserProfile();
                this.userInfo = response.data; // 更新 Store 中的用户信息
                localStorage.setItem(USER_INFO_KEY, JSON.stringify(response.data));
            } catch (error) {
                console.error('获取用户信息失败', error);
                this.logout(); // 获取失败可能需要强制登出
            }
        }
    }
});