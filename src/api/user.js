import axios from 'axios';

// 使用在 product.js 中定义的基础实例或重新创建一个
// 这里假设我们沿用统一的 service 实例配置
const service = axios.create({
    baseURL: 'http://localhost:8080/api', // 【重要】请确保 baseURL 正确
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
});

// ==========================================
// 2. 【新增部分】添加请求拦截器
// ==========================================
service.interceptors.request.use(
  config => {
    // 从 localStorage 获取登录时存入的 Token
    const token = localStorage.getItem('campus_user_token');
    
    if (token) {
      // 在请求头中注入 Authorization 字段
      // 注意：'Bearer ' 后面有一个空格，这必须和后端拦截器的解析逻辑一致
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    console.error('请求拦截器错误:', error);
    return Promise.reject(error);
  }
);
// ==========================================
// 3. 【新增部分】可选：添加响应拦截器 (处理 401)
// ==========================================
service.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      // 如果后端返回 401，说明 Token 过期或无效
      alert('登录已过期，请重新登录');
      localStorage.removeItem('campus_user_token');
      window.location.href = '/admin/login'; // 强制跳转回登录页
    }
    return Promise.reject(error);
  }
);

// **********************************
// 核心接口函数
// **********************************

/**
 * 用户登录接口
 * @param {object} data - 包含 username 和 password
 * @returns {Promise<object>} 后端返回的登录结果，应包含 token 和用户信息
 */
export function login(data) {
    // 对应后端接口：POST /api/user/login
    return service.post('/user/login', data);
}

/**
 * 用户注册接口
 * @param {object} data - 注册信息
 */
export function userRegister(data) {
    // 对应后端接口：POST /api/user/register
    return service.post('/user/register', data);
}

// 待实现接口：获取用户信息
export function getUserProfile() {
    // 对应后端接口：GET /api/user/profile
    return service.get('/user/profile');
}