import axios from 'axios';

// 1. 创建基础 Axios 实例
const service = axios.create({
    // 【重要】替换为您的Java EE后端地址！
    baseURL: 'http://localhost:8080/api', 
    timeout: 5000, 
    headers: {
        'Content-Type': 'application/json'
    }
});

// 2. 请求拦截器 (用于添加Token等，现在可以先留空)
service.interceptors.request.use(
    config => {
        // const token = localStorage.getItem('token');
        // if (token) {
        //     config.headers.Authorization = `Bearer ${token}`;
        // }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// 3. 响应拦截器 (用于处理错误码等)
service.interceptors.response.use(
    response => {
        // 假设后端返回的数据结构是 { code: 200, data: [...] }
        return response.data;
    },
    error => {
        // 统一处理接口报错
        console.error("API请求错误:", error.response || error.message);
        return Promise.reject(error);
    }
);


// **********************************
// 4. 核心接口函数
// **********************************

/**
 * 获取校园二手商品列表
 * @param {object} query - 查询参数，如 { categoryId: 1, keyword: '书本' }
 * @returns {Promise<object>} 后端返回的商品列表数据
 */
export function getProductList(query) {
    // 假设您的后端获取商品列表的接口是 /product/list
    return service.get('/product/list', { params: query });
}

// 可添加其他接口，如获取详情
export function getProductDetail(id) {
    return service.get(`/product/${id}`);
}