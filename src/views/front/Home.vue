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

    <el-button 
      type="warning" 
      icon="Plus" 
      size="large" 
      @click="publishDialogVisible = true" 
      circle 
      style="position: fixed; bottom: 80px; right: 50px; width: 65px; height: 65px; z-index: 99; box-shadow: 0 4px 12px rgba(0,0,0,0.2); font-size: 24px;" 
    >
      卖
    </el-button>

    <el-dialog v-model="publishDialogVisible" title="发布我的二手宝贝" width="500px" destroy-on-close>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :precision="2" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" rows="3" placeholder="描述一下宝贝的细节..." />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="publishDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handlePublish" :loading="publishLoading">立即发布</el-button>
      </template>
    </el-dialog>

    <footer class="footer">
      <p>© 2025 校园二手交易平台 - 助力校园绿色循环</p>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Search, Refresh, Picture, Plus } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { getProductList, getCategoryList, publishProduct } from '@/api/product';

// --- 原有逻辑变量 ---
const searchQuery = ref('');
const loading = ref(false);
const productList = ref([]);
const banners = ref([
  { text: '毕业季，学长学姐带你捡漏', color: '#409EFF' },
  { text: '低价转让，校友福利', color: '#67C23A' }
]);

// --- 新增：发布逻辑变量 ---
const publishDialogVisible = ref(false);
const publishLoading = ref(false);
const formRef = ref(null);
const categories = ref([]);
const form = ref({
  name: '',
  price: 0,
  categoryId: null,
  description: '',
  imageUrl: 'https://via.placeholder.com/150'
});

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
};

// --- 方法：获取列表 ---
const fetchProducts = async () => {
  loading.value = true;
  try {
    const res = await getProductList(1, 10, searchQuery.value);
    productList.value = res.data.list || res.data; 
  } catch (error) {
    ElMessage.error('获取商品失败');
  } finally {
    loading.value = false;
  }
};

// --- 方法：发布商品 ---
const handlePublish = async () => {
  if (!formRef.value) return;
  await formRef.value.validate(async (valid) => {
    if (valid) {
      publishLoading.value = true;
      try {
        await publishProduct(form.value);
        ElMessage.success('发布成功！');
        publishDialogVisible.value = false;
        fetchProducts(); // 刷新首页列表
      } catch (error) {
        ElMessage.error('发布失败');
      } finally {
        publishLoading.value = false;
      }
    }
  });
};

onMounted(() => {
  fetchProducts();
  // 加载分类
  getCategoryList().then(res => {
    categories.value = res.data;
  });
});

const handleSearch = () => {
  fetchProducts();
};
</script>

<看到了！确实，现在的界面虽然功能上有了那个“卖”字按钮，但布局略显空旷，特别是轮播图（Banner）和商品列表之间没有拉开层次感。

别急，我们来做个“美容手术”。界面变丑通常是因为：

配色太单一：背景色和白色卡片混在一起。

间距没拉开：内容全部挤在左边。

按钮太突兀：那个橙色按钮在全白背景下显得有点孤单。

🎨 界面美化方案（直接覆盖 <style> 部分）
我为你微调了样式，增加了背景层次感、卡片阴影和响应式布局优化。请把 Home.vue 最后的 <style scoped> 部分替换成这段：

CSS

<style scoped>
/* 全局背景优化 */
.home-wrapper {
  background-color: #f8f9fb; /* 浅灰蓝色背景，衬托白色卡片 */
  min-height: 100vh;
}

/* 导航栏美化 */
.nav-bar {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px); /* 磨砂玻璃效果 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  padding: 12px 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-content {
  max-width: 1280px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(45deg, #409eff, #36cfc9); /* 渐变色 Logo */
  -webkit-background-clip: text;
  color: transparent;
}

/* 搜索框美化 */
.search-input :deep(.el-input__wrapper) {
  border-radius: 20px;
  background-color: #f0f2f5;
  box-shadow: none !important;
}

/* 轮播图美化 */
.banner {
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 30px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.08);
}

.banner-item {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

/* 商品展示区 */
.main-content {
  max-width: 1280px;
  margin: 0 auto;
  padding: 20px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
  border-left: 5px solid #409eff;
  padding-left: 15px;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

/* 商品卡片深度美化 */
.product-card {
  border: none;
  border-radius: 12px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  overflow: hidden;
  margin-bottom: 25px;
}

.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 20px rgba(0, 0, 0, 0.1);
}

.image-wrapper {
  position: relative;
  height: 200px;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 15px;
  color: #303133;
  font-weight: 500;
  height: 40px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.product-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}

.price {
  color: #ff4d4f;
  font-size: 20px;
  font-weight: bold;
}

/* 悬浮按钮动效 */
.publish-btn {
  transition: all 0.3s;
}

.publish-btn:hover {
  transform: scale(1.1) rotate(5deg);
  box-shadow: 0 6px 16px rgba(230, 162, 60, 0.4);
}

.footer {
  margin-top: 50px;
  padding: 30px 0;
  border-top: 1px solid #ebeef5;
  color: #909399;
  text-align: center;
}
</style>