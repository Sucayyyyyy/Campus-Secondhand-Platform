<template>
  <div class="product-management">
    <div class="header-actions">
      <h2>商品管理</h2>
      <button @click="openForm(null)" class="add-btn">
        + 添加新商品
      </button>
    </div>

    <div class="filter-bar">
      <input v-model="searchKeyword" placeholder="输入商品名称搜索" @keyup.enter="fetchProducts" />
      <button @click="fetchProducts">搜索</button>
    </div>

    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>价格 (¥)</th>
            <th>分类</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in productList" :key="product.id">
            <td>{{ product.id }}</td>
            <td>{{ product.name }}</td>
            <td>{{ product.price.toFixed(2) }}</td>
            <td>{{ product.categoryName || '电子产品' }}</td>
            <td>
              <span :class="['status', product.status === 1 ? 'active' : 'inactive']">
                {{ product.status === 1 ? '上架中' : '已下架' }}
              </span>
            </td>
            <td class="actions">
              <button @click="openForm(product.id)" class="edit-btn">编辑</button>
              <button @click="handleDelete(product.id)" class="delete-btn">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="!productList.length && !isLoading" class="empty-data">
        暂无商品数据。
      </div>
       <div v-if="isLoading" class="loading-data">
        正在加载中...
      </div>
    </div>
    
    <ProductForm 
      v-if="isFormVisible" 
      :productId="currentProductId" 
      @close="closeForm"
      @saved="fetchProducts"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import ProductForm from './ProductForm.vue';
import { getProductList, deleteProduct } from '@/api/product';

const productList = ref([]);
const searchKeyword = ref('');
const isLoading = ref(false);

const isFormVisible = ref(false);
const currentProductId = ref(null);

// 1. 获取商品列表
const fetchProducts = async () => {
  isLoading.value = true;
  try {
    const result = await getProductList({ keyword: searchKeyword.value });
    // 假设后端返回的数据在 result.data 中，且是一个数组
    productList.value = result.data || [];
  } catch (error) {
    console.error('获取商品列表失败:', error);
  } finally {
    isLoading.value = false;
  }
};

// 2. 打开表单 (用于编辑或新增)
const openForm = (id) => {
  currentProductId.value = id; // id 为 null 表示新增
  isFormVisible.value = true;
};

// 3. 关闭表单
const closeForm = () => {
  isFormVisible.value = false;
  currentProductId.value = null;
};

// 4. 处理删除操作
const handleDelete = async (id) => {
  if (confirm(`确定要删除 ID 为 ${id} 的商品吗？`)) {
    try {
      await deleteProduct(id);
      alert('商品删除成功！');
      fetchProducts(); // 刷新列表
    } catch (error) {
      console.error('删除商品失败:', error);
      alert('删除失败，请检查后端服务。');
    }
  }
};

onMounted(fetchProducts); // 组件挂载时自动加载数据
</script>

<style scoped>
/* 样式代码与之前提供的基本一致，确保您粘贴了完整的 <style> 块 */
.product-management { padding: 20px; }
.header-actions { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.add-btn { background-color: #42b983; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; }
.filter-bar { margin-bottom: 20px; display: flex; gap: 10px; }
/* ... (其他样式) ... */
table { width: 100%; border-collapse: collapse; }
/* ... (表格样式) ... */
</style>