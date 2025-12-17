import request from '@/utils/request'; // 指向你封装好的 axios 实例

// 获取全部分类（用于下拉框）
export function getCategoryList() {
    return request({
        url: '/category/all',
        method: 'get'
    });
}

// 发布商品
export function publishProduct(data) {
    return request({
        url: '/product/publish',
        method: 'post',
        data
    });
}