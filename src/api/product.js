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
 * @param {object} query - 查询参数 (如 keyword, categoryId, page, size)
 */
export function getProductList(query) { 
    // 假设后端获取商品列表的接口是 GET /product/list
    return service.get('/product/list', { params: query });
}


/**
 * 删除指定ID的商品 (DELETE)
 * @param {number} id - 商品ID
 */
export function deleteProduct(id) {
    // 假设后端删除接口是 DELETE /product/{id}
    return service.delete(`/product/${id}`);
}

/**
 * 获取指定ID的商品详情 (GET)
 * @param {number} id - 商品ID
 */
export function getProductDetail(id) {
    // 假设后端获取详情接口是 GET /product/{id}
    return service.get(`/product/${id}`);
}

/**
 * 创建新商品 (POST)
 */
export function createProduct(data) {
    // 假设后端创建接口是 POST /product
    return service.post('/product', data);
}

/**
 * 更新商品信息 (PUT)
 */
export function updateProduct(id, data) {
    // 假设后端更新接口是 PUT /product/{id}
    return service.put(`/product/${id}`, data);
}