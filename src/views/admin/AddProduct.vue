<template>
  <div class="add-product-container">
    <el-card>
      <template #header>
        <div class="card-header"><span>发布新商品</span></div>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>

        <el-form-item label="商品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option 
              v-for="cat in categories" 
              :key="cat.id" 
              :label="cat.name" 
              :value="cat.id" 
            />
          </el-select>
        </el-form-item>

        <el-form-item label="商品价格" prop="price">
          <el-input-number v-model="form.price" :precision="2" :step="1" :min="0" />
          <span style="margin-left: 10px">元</span>
        </el-form-item>

        <el-form-item label="详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" rows="4" placeholder="描述商品新旧程度..." />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handlePublish" :loading="loading">立即发布</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getCategoryList, publishProduct } from '@/api/product'; // 导入你的 API

const formRef = ref(null);
const loading = ref(false);
const categories = ref([]); // 存放从后端拿到的分类

const form = ref({
  name: '',
  price: 0,
  categoryId: null,
  description: '',
  imageUrl: 'https://via.placeholder.com/150' // 暂时给个默认图
});

const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
};

//  页面加载时获取真实分类
onMounted(async () => {
  try {
    const res = await getCategoryList();
    categories.value = res.data;
  } catch (error) {
    ElMessage.error('获取分类列表失败');
  }
});


const handlePublish = async () => {
  if (!formRef.value) return;
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        await publishProduct(form.value);
        ElMessage.success('发布成功！');
        resetForm();
      } catch (error) {
        ElMessage.error('发布失败，请检查后端服务');
      } finally {
        loading.value = false;
      }
    }
  });
};

const resetForm = () => {
  formRef.value.resetFields();
};
</script>