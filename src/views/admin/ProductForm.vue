<template>
  <div class="modal-overlay" @click.self="emit('close')">
    <div class="modal-content">
      <h3>{{ isEdit ? '编辑商品' : '新增商品' }}</h3>
      
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="name">商品名称:</label>
          <input id="name" v-model="form.name" required />
        </div>

        <div class="form-group">
          <label for="price">价格 (¥):</label>
          <input id="price" type="number" step="0.01" v-model.number="form.price" required />
        </div>

        <div class="form-group">
          <label for="description">描述:</label>
          <textarea id="description" v-model="form.description"></textarea>
        </div>

        <div class="form-group">
          <label for="status">状态:</label>
          <select id="status" v-model.number="form.status">
            <option :value="1">上架中</option>
            <option :value="0">已下架</option>
          </select>
        </div>
        
        <div class="form-actions">
          <button type="button" @click="emit('close')" class="cancel-btn" :disabled="isLoading">取消</button>
          <button type="submit" class="submit-btn" :disabled="isLoading">
            {{ isLoading ? '保存中...' : '保存' }}
          </button>
        </div>
      </form>

    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, onMounted } from 'vue';
import { getProductDetail, createProduct, updateProduct } from '@/api/product';

const props = defineProps({
  // 传入的商品ID，null表示新增，有ID表示编辑
  productId: {
    type: [Number, null],
    default: null
  }
});

const emit = defineEmits(['close', 'saved']); // 定义组件事件

const isEdit = computed(() => props.productId !== null);
const isLoading = ref(false);

// 初始表单数据结构
const initialForm = {
  name: '',
  price: 0.00,
  description: '',
  status: 1, // 默认上架
  // categoryId: 1, // 假设默认分类
  // imageUrl: '', // 图片上传功能后续再加
};
const form = ref({ ...initialForm });

// 1. 编辑模式下：加载商品详情
const loadProductDetail = async () => {
  if (!isEdit.value) return; // 如果是新增，则不执行

  isLoading.value = true;
  try {
    const result = await getProductDetail(props.productId);
    // 使用返回的数据覆盖表单
    form.value = { 
      ...form.value, 
      ...result.data 
    };
  } catch (error) {
    console.error('加载商品详情失败:', error);
    alert('加载详情失败，请检查ID或网络。');
    emit('close'); // 加载失败则关闭表单
  } finally {
    isLoading.value = false;
  }
};

// 2. 提交表单
const handleSubmit = async () => {
  isLoading.value = true;
  try {
    let response;
    
    if (isEdit.value) {
      // 编辑逻辑：调用更新接口
      response = await updateProduct(props.productId, form.value);
      alert('商品更新成功！');
    } else {
      // 新增逻辑：调用创建接口
      response = await createProduct(form.value);
      alert('商品创建成功！');
    }

    emit('saved', response.data); // 通知父组件保存成功，并传入新数据
    emit('close'); // 关闭模态框

  } catch (error) {
    console.error('表单提交失败:', error);
    alert(`操作失败：${isEdit.value ? '更新' : '创建'}商品失败，请检查数据。`);
  } finally {
    isLoading.value = false;
  }
};

// 组件挂载时或 props.productId 变化时加载数据
onMounted(loadProductDetail);
// 监听 props.productId 变化（通常不需要，但在某些复杂场景下有用）
watch(() => props.productId, loadProductDetail);

</script>

<style scoped>
/* 模态框基础样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-content {
  background: white;
  padding: 30px;
  border-radius: 8px;
  width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
h3 { margin-top: 0; margin-bottom: 20px; text-align: center; }

/* 表单样式 */
.form-group { margin-bottom: 15px; }
.form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
.form-group input, .form-group textarea, .form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
.form-group textarea { resize: vertical; height: 100px; }

/* 按钮样式 */
.form-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
.submit-btn, .cancel-btn { padding: 10px 15px; border-radius: 4px; cursor: pointer; border: none; font-weight: bold; }
.submit-btn { background-color: #42b983; color: white; }
.cancel-btn { background-color: #e0e0e0; color: #333; }
.submit-btn:disabled { background-color: #a0d9b4; cursor: not-allowed; }
</style>