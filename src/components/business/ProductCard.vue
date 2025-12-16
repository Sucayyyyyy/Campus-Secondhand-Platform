<template>
  <div class="product-card" @click="goToDetail(product.id)">
    <img :src="product.imageUrl || 'default-image.png'" alt="商品图片" class="product-image">
    <div class="product-info">
      <h3 class="title">{{ product.name }}</h3>
      <p class="price">
        ¥ <span>{{ product.price.toFixed(2) }}</span>
      </p>
      <p class="location">{{ product.location || '某校园' }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 接收父组件传递的商品数据
const props = defineProps({
  product: {
    type: Object,
    required: true,
    // 定义商品对象的结构，方便开发
    default: () => ({ 
      id: 0, 
      name: '示例商品名称', 
      price: 0.00, 
      imageUrl: '', 
      location: '示例地点' 
    })
  }
});

// 点击卡片跳转到商品详情页
const goToDetail = (id) => {
  // 假设详情页的路由是 /detail/:id
  router.push(`/detail/${id}`);
};
</script>

<style scoped>
.product-card {
  /* 基础卡片样式 */
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: transform 0.2s;
  background-color: #fff;
}
.product-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.product-image {
  width: 100%;
  height: 150px; /* 固定高度 */
  object-fit: cover; /* 保证图片不变形 */
  display: block;
}
.product-info {
  padding: 10px;
}
.title {
  font-size: 16px;
  font-weight: bold;
  margin-top: 0;
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.price {
  color: #ff4500; /* 突出价格 */
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}
.location {
  font-size: 12px;
  color: #999;
  text-align: right;
}
</style>