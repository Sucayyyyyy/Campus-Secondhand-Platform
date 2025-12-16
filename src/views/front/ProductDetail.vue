<template>
  <div class="detail-page-container">
    <div v-if="loading" class="loading-state">
      正在加载商品信息...
    </div>
    
    <div v-else-if="product" class="detail-content">
      <div class="product-image-section">
        <img :src="product.imageUrl || 'default-image.png'" alt="商品主图" class="main-image">
      </div>

      <div class="product-info-section">
        <h1 class="product-title">{{ product.name }}</h1>
        
        <div class="price-box">
          <span class="label">二手价：</span>
          <span class="price-value">¥ {{ product.price ? product.price.toFixed(2) : '0.00' }}</span>
        </div>

        <div class="meta-info">
          <p>
            <span class="label">分类：</span>
            {{ product.categoryName || '未知分类' }}
          </p>
          <p>
            <span class="label">发布者：</span>
            {{ product.userName || '匿名用户' }}
          </p>
          <p>
            <span class="label">发布地点：</span>
            {{ product.location || '校园内' }}
          </p>
        </div>

        <div class="description-box">
          <h3 class="section-title">商品描述</h3>
          <p>{{ product.description || '该商品没有详细描述。' }}</p>
        </div>

        <div class="contact-box">
          <button @click="showContact = true" class="contact-btn">
            联系卖家
          </button>
          
          <div v-if="showContact" class="contact-details">
            <p>
              <span class="label">手机：</span>
              {{ product.contactPhone || '暂无' }}
            </p>
            <p>
              <span class="label">微信：</span>
              {{ product.contactWechat || '暂无' }}
            </p>
          </div>
        </div>

      </div>
    </div>
    
    <div v-else class="error-state">
      <p>商品未找到或已下架。</p>
      <router-link to="/" class="back-home">返回首页</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getProductDetail } from '@/api/product'; // 确保这个接口已在 product.js 中定义

const route = useRoute();
const product = ref(null);
const loading = ref(true);
const showContact = ref(false); // 控制联系方式的显示

const fetchProductDetail = async () => {
  const productId = route.params.id; // 从路由参数中获取商品ID
  if (!productId) {
    loading.value = false;
    return;
  }
  
  loading.value = true;
  try {
    // 调用后端接口
    const result = await getProductDetail(productId);
    
    // 假设后端返回的数据在 result.data 中
    if (result.data) {
      product.value = result.data;
    } else {
      product.value = null; // 数据为空，显示未找到
    }

  } catch (error) {
    console.error('获取商品详情失败:', error);
    product.value = null; // 接口失败，显示未找到
  } finally {
    loading.value = false;
  }
};

onMounted(fetchProductDetail);
</script>

<style scoped>
.detail-page-container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 20px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}
.detail-content {
  display: flex;
  gap: 40px;
}
.product-image-section {
  flex: 1;
  max-width: 50%;
  border-radius: 8px;
  overflow: hidden;
}
.main-image {
  width: 100%;
  height: auto;
  display: block;
  object-fit: cover;
}
.product-info-section {
  flex: 1;
  max-width: 50%;
  padding-right: 20px;
}
.product-title {
  font-size: 28px;
  color: #333;
  margin-top: 0;
  margin-bottom: 20px;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}
.price-box {
  background-color: #f7f7f7;
  padding: 15px;
  border-radius: 6px;
  margin-bottom: 20px;
}
.label {
  font-weight: bold;
  color: #666;
  margin-right: 10px;
}
.price-value {
  font-size: 32px;
  color: #e74c3c; /* 醒目的红色 */
  font-weight: 700;
}
.meta-info p {
  margin: 5px 0;
  font-size: 16px;
}
.description-box {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px dashed #ccc;
}
.section-title {
  font-size: 20px;
  color: #444;
  margin-bottom: 10px;
}
.contact-box {
  margin-top: 30px;
  padding-top: 20px;
}
.contact-btn {
  background-color: #42b983;
  color: white;
  padding: 12px 25px;
  border: none;
  border-radius: 6px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.contact-details {
  margin-top: 15px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background-color: #fffaf0;
}
.loading-state, .error-state {
  text-align: center;
  padding: 50px;
  font-size: 18px;
  color: #999;
}
.back-home {
  display: inline-block;
  margin-top: 15px;
  color: #3498db;
  text-decoration: none;
}
</style>