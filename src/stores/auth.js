import { defineStore } from 'pinia';
// 【修正 1】：导入时使用 as 起别名，避免和下面的 login action 同名冲突
import { login as loginApi, getUserProfile } from '@/api/user'; 


const TOKEN_KEY = 'campus_user_token';
const USER_INFO_KEY = 'campus_user_info';

export const useAuthStore = defineStore('auth', {
   state: () => {
        // 1. 先把字符串取出来
        const savedUserInfo = localStorage.getItem(USER_INFO_KEY);
        
        return {
            token: localStorage.getItem(TOKEN_KEY) || null,
            // 2. 【核心修复】：增加逻辑判断，只有当字符串存在且不是 "undefined" 时才解析
            userInfo: (savedUserInfo && savedUserInfo !== "undefined") 
                       ? JSON.parse(savedUserInfo) 
                       : null,
            isLoggedIn: !!localStorage.getItem(TOKEN_KEY),
        };
    },
    
    actions: {
        async login(username, password) {
            try {
                // 【修正 2】：使用别名调用 API 接口
                // 这里的 res 结构取决于你在 axios 拦截器里的设置
                const res = await loginApi({ username, password });
                
                // 【修正 3】：健壮性处理
                // 如果后端返回的是 { success, token, user }，直接解构 res.data
                // 如果 axios 拦截器已经 return 了 response.data，则直接解构 res
                const data = res.data || res;
                const { token, user } = data; 

                if (!token) {
                    throw new Error('登录响应中未获取到 Token');
                }

                // 3. 存储状态到 Store
                this.token = token;
                this.userInfo = user;
                this.isLoggedIn = true;
                
                // 4. 存储到本地（统一使用常量 TOKEN_KEY）
                localStorage.setItem(TOKEN_KEY, token);
                localStorage.setItem(USER_INFO_KEY, JSON.stringify(user));
                
                return true; 
            } catch (error) {
                this.logout(); 
                console.error('Store 登录失败:', error);
                throw error; 
            }
        },

        logout() {
            this.token = null;
            this.userInfo = null;
            this.isLoggedIn = false;
            localStorage.removeItem(TOKEN_KEY);
            localStorage.removeItem(USER_INFO_KEY);
        },

        async fetchProfile() {
            if (!this.token) return;
            try {
                const response = await getUserProfile();
                const data = response.data || response;
                this.userInfo = data;
                localStorage.setItem(USER_INFO_KEY, JSON.stringify(data));
            } catch (error) {
                console.error('获取用户信息失败', error);
                this.logout();
            }
        }
    }
});