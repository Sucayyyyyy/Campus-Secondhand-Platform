<template>
  <div class="home-page">
    
    <header class="main-header">
      <div class="header-content">
        <div class="logo">LOGO / 二手平台</div>
        <nav class="main-nav">
          <a href="/" class="nav-item active">首页</a>
          <a href="/mylisting" class="nav-item">我的发布</a>
          <a href="/admin/login" class="nav-item">登录/注册</a>
        </nav>
        <div class="user-profile">
          <div class="avatar">L</div>
          <span>您好, Leo!</span>
        </div>
      </div>
    </header>

    <div class="search-section">
      <h1 class="platform-title">校园二手交易平台</h1>
      <div class="search-bar-container">
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="搜索您想要的二手物品..." 
          class="search-input"
        />
        <button @click="handleSearch" class="search-button">搜索</button>
      </div>
    </div>

    <div class="content-section">
      <h2 class="section-title">最新发布</h2>
      
      <div v-if="isLoading" class="loading">加载中...</div>
      
      <div v-else-if="productList.length === 0" class="empty-state">
        暂无商品发布。
      </div>

      <div v-else class="product-grid">
        <ProductCard 
          v-for="product in productList" 
          :key="product.id" 
          :product="product" 
        />
      </div>
    </div>

    <footer class="main-footer">
      <p>Copyright © 校园二手交易平台 | All Rights Reserved.</p>
      <div class="social-links">
        <span>联系我们</span>
      </div>
    </footer>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import ProductCard from '@/components/business/ProductCard.vue'; // 确保路径正确

// 状态和数据
const searchQuery = ref('');
const isLoading = ref(false);

// -------------------------------------------------------------------
// 模拟商品数据：用于展示界面的美化效果
// -------------------------------------------------------------------
const productList = ref([
    { id: 1, name: '9成新笔记本电脑', price: 2500.00, location: '图书馆', imageUrl: 'laptop' },
    { id: 2, name: '二手吉他一把', price: 300.00, location: '宿舍楼', imageUrl: 'guitar' },
    { id: 3, name: '高数课本（带笔记）', price: 35.50, location: '教学楼', imageUrl: 'book' }
]);

// 搜索逻辑
const handleSearch = () => {
    console.log('搜索内容:', searchQuery.value);
    // TODO: 实现 API 搜索逻辑
};

// 实际项目应在 onMounted 中调用 API 获取数据
onMounted(() => {
    // fetchProducts(); // 实际项目中取消注释，调用真正的获取数据函数
});

// 真正的获取数据函数 (示例)
const fetchProducts = async () => {
    isLoading.value = true;
    try {
        // const response = await getProductList(); // 调用 API
        // productList.value = response.data;
    } catch (error) {
        console.error('获取商品列表失败:', error);
    } finally {
        isLoading.value = false;
    }
};
</script>


<style scoped>
/* ===================================================================
   整体布局与基础样式
   =================================================================== */
.home-page {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f7f9fb; /* 柔和浅灰色背景 */
}

/* ===================================================================
   1. 顶部导航栏 (Header) 样式
   =================================================================== */
.main-header {
    background-color: white;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    padding: 0 30px;
}

.header-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
}

.logo {
    font-size: 20px;
    font-weight: bold;
    color: #333;
}

.main-nav {
    display: flex;
    gap: 25px;
}

.nav-item {
    color: #555;
    text-decoration: none;
    font-size: 15px;
    padding: 2px 0;
    transition: color 0.3s;
}

.nav-item:hover, .nav-item.active {
    color: #42b983; /* 品牌色 */
    border-bottom: 2px solid #42b983;
}

.user-profile {
    display: flex;
    align-items: center;
    gap: 10px;
    color: #555;
}

.avatar {
    width: 30px;
    height: 30px;
    background-color: #e0e0e0;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
}


/* ===================================================================
   2. 中心搜索区
   =================================================================== */
.search-section {
    padding: 50px 20px 70px;
    text-align: center;
    background-color: #fff; /* 搜索区背景可以为白色 */
    border-bottom: 1px solid #eee;
}

.platform-title {
    font-size: 36px;
    font-weight: 700;
    color: #333;
    margin-bottom: 30px;
}

.search-bar-container {
    display: flex;
    justify-content: center;
    max-width: 600px;
    margin: 0 auto;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08); /* 搜索框阴影 */
    border-radius: 8px;
    overflow: hidden; /* 确保子元素不超出圆角 */
}

.search-input {
    flex-grow: 1;
    padding: 15px 20px;
    border: none;
    font-size: 16px;
    outline: none;
}

.search-button {
    padding: 15px 25px;
    background-color: #42b983;
    color: white;
    border: none;
    cursor: pointer;
    font-size: 16px;
    font-weight: 600;
    transition: background-color 0.3s;
}

.search-button:hover {
    background-color: #369c70;
}


/* ===================================================================
   3. 商品展示区
   =================================================================== */
.content-section {
    flex-grow: 1;
    max-width: 1200px;
    margin: 30px auto;
    padding: 0 20px;
    width: 100%;
}

.section-title {
    font-size: 24px;
    font-weight: 600;
    color: #333;
    margin-bottom: 25px;
    border-left: 4px solid #42b983; /* 增加品牌色装饰线 */
    padding-left: 10px;
}

.product-grid {
    display: grid;
    /* 响应式网格：最小宽度 280px，自动填充 */
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 25px;
}

.empty-state {
    text-align: center;
    color: #999;
    padding: 50px;
    background: white;
    border-radius: 8px;
}


/* ===================================================================
   4. 底部页脚 (Footer) 样式
   =================================================================== */
.main-footer {
    background-color: #333;
    color: #ccc;
    padding: 20px 30px;
    text-align: center;
    font-size: 14px;
    margin-top: auto; /* 保持页脚在最底部 */
}

.social-links {
    margin-top: 10px;
}

.social-links span {
    margin: 0 10px;
    cursor: pointer;
}
</style>