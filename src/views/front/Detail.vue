<template>
  <div class="detail-container" v-loading="loading">
    <el-page-header @back="$router.back()" content="商品详情" style="margin-bottom: 20px" />
    
    <el-row :gutter="40" v-if="product">
      <el-col :md="10" :sm="24">
        <el-image 
          :src="product.imageUrl || product.image_url" 
          fit="cover" 
          class="main-image"
        >
          <template #error><div class="image-slot"><el-icon><Picture /></el-icon></div></template>
        </el-image>
      </el-col>

      <el-col :md="14" :sm="24">
        <h1 class="title">{{ product.name }}</h1>
        <div class="price-tag">￥{{ product.price }}</div>
        <el-divider />
        <p class="description">{{ product.description || '暂无详细描述' }}</p>
        <div class="action-bar">
          <el-button type="primary" size="large">想要联系卖家</el-button>
          <el-button type="warning" size="large" plain>收藏</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Picture } from '@element-plus/icons-vue'
import request from '@/utils/request' // 或者你封装的 api 文件

const route = useRoute()
const loading = ref(false)
const product = ref(null)

const getDetail = async () => {
  loading.value = true
  try {
    const id = route.params.id
    // 💡 这里的路径要对应你刚写的后端接口
    const res = await request.get(`/api/product/${id}`)
    // 这里的解析逻辑要和你首页那里的 fetchProducts 保持一致
    product.value = res.data.success ? res.data.data : res.data
  } finally {
    loading.value = false
  }
}

onMounted(() => { getDetail() })
</script>

<style scoped>
.detail-container { max-width: 1000px; margin: 20px auto; padding: 20px; background: #fff; border-radius: 8px; }
.main-image { width: 100%; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.title { font-size: 28px; margin-bottom: 10px; }
.price-tag { font-size: 32px; color: #f56c6c; font-weight: bold; }
.description { line-height: 1.8; color: #606266; min-height: 150px; }
.action-bar { margin-top: 40px; }
</style>