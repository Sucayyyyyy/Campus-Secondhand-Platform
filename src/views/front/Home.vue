<template>
  <div class="home-container">
    <header class="header">
      <h1>校园二手交易平台</h1>
      <div class="search-bar">
        <input 
          type="text" 
          v-model="searchKeyword" 
          placeholder="搜索您想要的二手物品..."
          @keyup.enter="fetchProducts"
        />
        <button @click="fetchProducts">搜索</button>
      </div>
    </header>

    <main class="main-content">
      <h2>最新发布</h2>
      <div v-if="isLoading" class="loading">加载中...</div>
      <div v-else-if="productList.length === 0" class="empty">暂无商品发布。</div>

      <div v-else class="product-grid">
        <ProductCard 
          v-for="product in productList" 
          :key="product.id" 
          :product="product" 
        />
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// 导入我们刚才创建的组件和API
import ProductCard from '@/components/business/ProductCard.vue';
import { getProductList } from '@/api/product'; 

// 商品列表数据
const productList = ref([]); 
// 搜索关键字
const searchKeyword = ref('');
// 加载状态
const isLoading = ref(false);

// 异步获取商品列表的函数
const fetchProducts = async () => {
  isLoading.value = true;
  try {
    // 携带搜索关键字作为查询参数
    const params = { keyword: searchKeyword.value };
    // 调用封装的后端接口
    const result = await getProductList(params);

    // 【重要】根据您的后端实际返回结构调整这里
    // 假设后端返回的数据在 result.data 中，且是一个数组
    if (result && result.data && Array.isArray(result.data)) {
        productList.value = result.data;
    } else {
        // 如果后端返回的不是数组，可能需要处理错误或默认值
        productList.value = [];
        console.warn("后端返回的商品列表数据结构不正确:", result);
    }
  } catch (error) {
    console.error('获取商品列表失败:', error);
    // 可以在这里显示一个错误提示给用户
    productList.value = [];
  } finally {
    isLoading.value = false;
  }
};

// 组件加载完成后立即获取一次数据
onMounted(() => {
  fetchProducts();
});
</script>

<style scoped>
/* 简单的样式布局 */
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
.header {
  text-align: center;
  margin-bottom: 20px;
}
.search-bar {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}
.search-bar input {
  padding: 10px;
  width: 400px;
  border: 2px solid #ddd;
  border-radius: 5px 0 0 5px;
  font-size: 16px;
}
.search-bar button {
  padding: 10px 20px;
  background-color: #42b983; /* Vue 绿色 */
  color: white;
  border: none;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  font-size: 16px;
}
.main-content h2 {
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 20px;
}
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); /* 响应式网格布局 */
  gap: 20px;
}
.loading, .empty {
  text-align: center;
  padding: 50px;
  color: #888;
}
</style>