<template>
  <div class="add-product-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>发布新商品</span>
        </div>
      </template>

      <el-form :model="productForm" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="商品名称" prop="title">
          <el-input v-model="productForm.title" placeholder="请输入商品名称，如：九成新考研数学资料" />
        </el-form-item>

        <el-form-item label="商品分类" prop="categoryId">
          <el-select v-model="productForm.categoryId" placeholder="请选择分类">
            <el-option label="数码产品" value="1" />
            <el-option label="图书教材" value="2" />
            <el-option label="生活用品" value="3" />
            <el-option label="运动器材" value="4" />
          </el-select>
        </el-form-item>

        <el-form-item label="商品价格" prop="price">
          <el-input-number v-model="productForm.price" :precision="2" :step="0.1" :min="0" />
          <span style="margin-left: 10px">元</span>
        </el-form-item>

        <el-form-item label="详细描述" prop="description">
          <el-input v-model="productForm.description" type="textarea" rows="4" placeholder="请输入成色、购买渠道、是否有瑕疵等..." />
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :limit="3"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm" :loading="loading">立即发布</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const formRef = ref(null)
const loading = ref(false)

const productForm = reactive({
  title: '',
  categoryId: '',
  price: 0,
  description: ''
})

const rules = {
  title: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  description: [{ required: true, message: '请输入商品描述', trigger: 'blur' }]
}

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate((valid) => {
    if (valid) {
      loading.ref = true
      // 模拟后端请求
      setTimeout(() => {
        console.log('提交的数据：', productForm)
        ElMessage.success('商品发布成功！')
        loading.ref = false
        resetForm()
      }, 1000)
    }
  })
}

const resetForm = () => {
  formRef.value.resetFields()
}
</script>

<style scoped>
.add-product-container {
  max-width: 800px;
  margin: 0 auto;
}
.card-header {
  font-weight: bold;
}
</style>