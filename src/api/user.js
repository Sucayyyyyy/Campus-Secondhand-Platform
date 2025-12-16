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

// **********************************
// 核心接口函数
// **********************************

/**
 * 用户登录接口
 * @param {object} data - 包含 username 和 password
 * @returns {Promise<object>} 后端返回的登录结果，应包含 token 和用户信息
 */
export function userLogin(data) {
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