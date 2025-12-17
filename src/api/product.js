import request from '@/utils/request' // 统一使用这个

/**
 * 1. 获取所有商品列表 (首页/管理页通用)
 */
export function getAllProducts(query) { 
    return request({
        url: '/product/list',
        method: 'get',
        params: query
    })
}

// 别名，为了兼容你之前的代码调用
export const getProductList = getAllProducts;

/**
 * 2. 获取分类列表
 */
export const getCategoryList = () => {
  return request({
    url: '/category/list',
    method: 'get'
  })
}

/**
 * 3. 发布/创建新商品
 */
export const publishProduct = (data) => {
  return request({
    url: '/product/publish',
    method: 'post',
    data
  })
}

// 别名，为了兼容你刚才写的函数名
export const createProduct = publishProduct;

/**
 * 4. 获取指定ID的商品详情
 */
export function getProductDetail(id) {
    return request({
        url: `/product/${id}`,
        method: 'get'
    })
}

/**
 * 5. 删除商品
 */
export function deleteProduct(id) {
    return request({
        url: `/product/${id}`,
        method: 'delete'
    })
}

/**
 * 6. 更新商品信息
 */
export function updateProduct(id, data) {
    return request({
        url: `/product/${id}`,
        method: 'put',
        data
    })
}