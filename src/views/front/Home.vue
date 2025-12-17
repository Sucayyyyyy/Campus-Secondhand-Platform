<template>
  <div class="home-wrapper">
    <div class="nav-bar">
      <div class="nav-content">
        <div class="logo">校园二手市场</div>
        <div class="search-area">
          <el-input
            v-model="searchQuery"
            placeholder="搜索你想要的商品..."
            :prefix-icon="Search"
            class="search-input"
            clearable
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
      <el-carousel height="280px" class="banner" :interval="5000" arrow="always">
        <el-carousel-item v-for="(item, index) in banners" :key="index">
          <div class="banner-item" :style="{ backgroundColor: item.color }">
            <h2>{{ item.text }}</h2>
          </div>
        </el-carousel-item>
      </el-carousel>

      <div class="product-section">
        <div class="section-header">
          <h3 class="section-title">最新发布</h3>
          <el-button link @click="fetchProducts">刷新列表 <el-icon><Refresh /></el-icon></el-button>
        </div>

        <div v-loading="loading" element-loading-text="正在努力加载商品...">
          <el-row :gutter="20">
            <el-col 
              :xs="12" :sm="8" :md="6" :lg="4.8" 
              v-for="item in productList" 
              :key="item.id"
              class="product-col"
            >
              <el-card 
                :body-style="{ padding: '0px' }" 
                shadow="hover" 
                class="product-card"
                @click="$router.push(`/detail/${item.id}`)"
              >
                <div class="image-wrapper">
                  <img 
                   v-if="item.imageUrl || item.image_url" 
                   :src="item.imageUrl || item.image_url" 
                    class="product-image" 
                    @error="(e) => { e.target.src = 'https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png' }"
                    />
                  <div v-else class="image-placeholder">
                    <el-icon :size="40" color="#909399"><Picture /></el-icon>
                  </div>
                </div>
                
                <div class="product-info">
                  <div class="name-box">
                    <span class="product-name">{{ item.name }}</span>
                  </div>
                  <div class="product-bottom">
                    <span class="price">￥{{ item.price }}</span>
                    <el-button text class="detail-btn">查看详情</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <el-empty v-if="!loading && productList.length === 0" description="暂无商品发布" />
        </div>
      </div>
    </el-main>

    <footer class="footer">
      <p>© 2025 校园二手交易平台 - 助力校园绿色循环</p>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Search, Picture, Refresh } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
// 💡 确保这里只导入 getAllProducts，避免重名冲突
import { getAllProducts } from '@/api/product'; 

const searchQuery = ref('');
const loading = ref(false);
const productList = ref([]);

const banners = [
  { text: '毕业季，书本大清仓', color: '#409EFF' },
  { text: '寻找心动的数码好物', color: '#67C23A' }
];

// 获取商品数据的主函数
const fetchProducts = async () => {
  loading.value = true;
  try {
    const res = await getAllProducts({ keyword: searchQuery.value });
    console.log(' 后端返回的完整结构:', res.data); 

    //  核心修正：既然返回的是 (2) [{…}, {…}]，说明 res.data 就是数组
    if (res.data && Array.isArray(res.data)) {
      productList.value = res.data;
      console.log(' 成功加载商品:', productList.value.length, '件');
    } 
    // 兼容部分带 Result 包装的情况
    else if (res.data && res.data.data) {
      productList.value = Array.isArray(res.data.data) ? res.data.data : res.data.data.list;
    } 
    else {
      ElMessage.warning('未能识别的数据格式');
    }
  } catch (error) {
    console.error(' 请求发送失败:', error);
    ElMessage.error('无法连接服务器');
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchProducts();
});

const handleSearch = () => {
  fetchProducts(); 
};
</script>

<style scoped>
/* 保持你之前的精美样式不变 */
.home-wrapper { background-color: #f5f7fa; min-height: 100vh; }
.nav-bar { background-color: #fff; box-shadow: 0 2px 12px 0 rgba(0,0,0,0.05); position: sticky; top: 0; z-index: 100; }
.nav-content { max-width: 1200px; margin: 0 auto; height: 64px; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
.logo { font-size: 22px; font-weight: bold; color: #409EFF; }
.search-input { width: 400px; }
.main-content { max-width: 1200px; margin: 0 auto; padding: 20px; }
.banner { border-radius: 12px; overflow: hidden; margin-bottom: 30px; }
.banner-item { height: 100%; display: flex; align-items: center; justify-content: center; color: white; }
.section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.section-title { font-size: 22px; font-weight: bold; }
.product-card { cursor: pointer; border-radius: 8px; margin-bottom: 20px; transition: all 0.3s ease; border: none; }
.product-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important; }
.image-wrapper { height: 200px; background-color: #f0f2f5; overflow: hidden; }
.product-image { width: 100%; height: 100%; object-fit: cover; }
.image-placeholder { height: 100%; display: flex; align-items: center; justify-content: center; }
.product-info { padding: 12px; }
.name-box { height: 42px; margin-bottom: 8px; }
.product-name { font-size: 14px; color: #303133; line-height: 1.5; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.product-bottom { display: flex; justify-content: space-between; align-items: center; }
.price { color: #f56c6c; font-weight: bold; font-size: 18px; }
.detail-btn { padding: 0; font-size: 12px; }
.footer { text-align: center; padding: 40px 0; color: #909399; font-size: 14px; }
</style>