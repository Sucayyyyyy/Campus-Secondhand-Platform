<template>
  <div class="home-wrapper">
    <div class="nav-bar">
      <div class="nav-content">
        <div class="logo">校园二手市场</div>
        <div class="search-area">
          <el-input
            v-model="searchQuery"
            placeholder="搜学姐的电脑、学长的考研书..."
            :prefix-icon="Search"
            class="search-input"
            @keyup.enter="handleSearch"
          />
        </div>
        <div class="nav-links">
          <el-button link @click="$router.push('/home')">首页</el-button>
          <el-button type="primary" plain @click="$router.push('/admin')">进入后台</el-button>
        </div>
      </div>
    </div>

    <el-main class="main-content">
      <el-carousel height="280px" class="banner">
        <el-carousel-item v-for="item in banners" :key="item">
          <div class="banner-item" :style="{ backgroundColor: item.color }">
            <h2>{{ item.text }}</h2>
          </div>
        </el-carousel-item>
      </el-carousel>

      <div class="product-section">
        <h3 class="section-title">最新发布</h3>
        <el-row :gutter="20">
          <el-col 
            :xs="12" :sm="8" :md="6" :lg="4.8" 
            v-for="product in productList" 
            :key="product.id"
            class="product-col"
          >
            <el-card 
              :body-style="{ padding: '0px' }" 
              shadow="hover" 
              class="product-card"
              @click="$router.push(`/detail/${product.id}`)"
            >
              <div class="image-placeholder">
                <img v-if="product.imageUrl" :src="product.imageUrl" class="image" />
                <el-icon v-else class="empty-icon"><Picture /></el-icon>
              </div>
              <div class="info">
                <div class="product-name">{{ product.name }}</div>
                <div class="product-bottom">
                  <span class="price">¥ {{ product.price }}</span>
                  <span class="category">{{ product.categoryName }}</span>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-main>

    <footer class="footer">
      <p>© 2023 校园二手交易平台 - 助力校园绿色循环</p>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Search, Picture } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { getAllProducts } from '@/api/product'; // 导入新接口

const searchQuery = ref('');
const loading = ref(false);
const productList = ref([]); // 初始为空列表

// 模拟的 Banner 数据
const banners = [
  { text: '毕业季，书本大清仓', color: '#409EFF' },
  { text: '寻找心动的数码好物', color: '#67C23A' }
];

//  获取真实数据的函数
const fetchProducts = async () => {
  loading.value = true;
  try {
    const res = await getAllProducts();
    // 假设后端返回的数据在 res.data 中
    productList.value = res.data; 
  } catch (error) {
    console.error('获取商品失败:', error);
    ElMessage.error('无法加载商品列表，请检查网络或后端');
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchProducts(); // 页面一加载就去拿数据
});

const handleSearch = () => {
  // 简单的本地搜索过滤演示（可选）
  console.log('搜索内容：', searchQuery.value);
};
</script>

<style scoped>
.home-wrapper {
  background-color: #f5f7fa;
  min-height: 100vh;
}

.nav-bar {
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.05);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.search-input {
  width: 450px;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.banner {
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 30px;
}

.banner-item {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.section-title {
  margin-bottom: 20px;
  font-size: 22px;
  font-weight: bold;
}

.product-card {
  cursor: pointer;
  border-radius: 8px;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.product-card:hover {
  transform: translateY(-5px);
}

.image-placeholder {
  height: 180px;
  background-color: #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: center;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.info {
  padding: 12px;
}

.product-name {
  font-size: 15px;
  height: 40px;
  overflow: hidden;
  margin-bottom: 10px;
}

.product-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  color: #f56c6c;
  font-weight: bold;
  font-size: 18px;
}

.category {
  font-size: 12px;
  color: #909399;
  background: #f0f2f5;
  padding: 2px 6px;
  border-radius: 4px;
}

.footer {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}
</style>