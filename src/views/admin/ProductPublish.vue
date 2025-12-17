<template>
  <div class="publish-container">
    <h2>发布二手商品</h2>
    <form @submit.prevent="handlePublish">
      <input v-model="form.name" placeholder="商品名称" required />
      <input v-model.number="form.price" type="number" placeholder="价格" required />
      
      <select v-model="form.categoryId">
        <option v-for="cat in categories" :key="cat.id" :value="cat.id">
          {{ cat.name }}
        </option>
      </select>
      
      <textarea v-model="form.description" placeholder="描述商品新旧程度..."></textarea>
      <button type="submit">立即发布</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCategoryList, publishProduct } from '@/api/product';

const categories = ref([]);
const form = ref({
  name: '',
  price: 0,
  categoryId: null,
  description: '',
  imageUrl: '' // 先放一个空的，以后对接上传接口
});

onMounted(async () => {
  // 页面加载时先获取分类列表
  const res = await getCategoryList();
  categories.value = res.data;
});

const handlePublish = async () => {
  try {
    await publishProduct(form.value);
    alert('发布成功！');
  } catch (error) {
    alert('发布失败，请检查登录状态');
  }
};
</script>