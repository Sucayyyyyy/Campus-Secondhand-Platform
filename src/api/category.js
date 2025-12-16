import axios from 'axios';

// 假设沿用统一的 service 实例配置
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
 * 获取分类列表 (GET)
 */
export function getCategoryList() {
    // 假设后端接口是 GET /category/list 或 /categories
    return service.get('/category/list');
}

/**
 * 创建新分类 (POST)
 * @param {object} data - 包含分类名称 (name)
 */
export function createCategory(data) {
    // 假设后端接口是 POST /category
    return service.post('/category', data);
}

/**
 * 更新分类信息 (PUT)
 * @param {number} id - 分类ID
 * @param {object} data - 包含分类名称 (name)
 */
export function updateCategory(id, data) {
    // 假设后端接口是 PUT /category/{id}
    return service.put(`/category/${id}`, data);
}

/**
 * 删除分类 (DELETE)
 * @param {number} id - 分类ID
 */
export function deleteCategory(id) {
    // 假设后端接口是 DELETE /category/{id}
    return service.delete(`/category/${id}`);
}

// 供前端使用的默认分类列表（如果后端没数据时）
export const MOCK_CATEGORIES = [
    { id: 1, name: '电子产品' },
    { id: 2, name: '学习资料' },
    { id: 3, name: '体育器材' },
    { id: 4, name: '日常用品' },
];