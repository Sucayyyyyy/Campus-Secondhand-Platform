<template>
  <div class="category-management">
    <div class="header-actions">
      <h2>分类管理</h2>
      <button @click="openForm(null, '')" class="add-btn">
        + 添加新分类
      </button>
    </div>

    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>分类名称</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="category in categoryList" :key="category.id">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td class="actions">
              <button @click="openForm(category.id, category.name)" class="edit-btn">编辑</button>
              <button @click="handleDelete(category.id)" class="delete-btn">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="!categoryList.length && !isLoading" class="empty-data">
        暂无分类数据。
      </div>
      <div v-if="isLoading" class="loading-data">
        正在加载中...
      </div>
    </div>
    
    <div v-if="isFormVisible" class="modal-overlay" @click.self="closeForm">
      <div class="modal-content">
        <h3>{{ currentCategoryId ? '编辑分类' : '新增分类' }}</h3>
        
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label for="name">分类名称:</label>
            <input id="name" v-model="categoryName" required />
          </div>
          
          <div class="form-actions">
            <button type="button" @click="closeForm" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn" :disabled="isSaving">
              {{ isSaving ? '保存中...' : '保存' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCategoryList, createCategory, updateCategory, deleteCategory, MOCK_CATEGORIES } from '@/api/category';

const categoryList = ref([]);
const isLoading = ref(false);
const isSaving = ref(false);

// 表单状态
const isFormVisible = ref(false);
const currentCategoryId = ref(null); // null表示新增
const categoryName = ref(''); // 用于表单输入的名称

// 1. 获取分类列表
const fetchCategories = async () => {
  isLoading.value = true;
  try {
    const result = await getCategoryList();
    // 假设后端返回的数据在 result.data 中
    categoryList.value = result.data || MOCK_CATEGORIES; 
  } catch (error) {
    console.error('获取分类列表失败:', error);
    // 如果获取失败，也可以显示 mock 数据
    categoryList.value = MOCK_CATEGORIES;
  } finally {
    isLoading.value = false;
  }
};

// 2. 打开表单
const openForm = (id, name) => {
  currentCategoryId.value = id;
  categoryName.value = name;
  isFormVisible.value = true;
};

// 3. 关闭表单
const closeForm = () => {
  isFormVisible.value = false;
  currentCategoryId.value = null;
  categoryName.value = '';
};

// 4. 处理表单提交 (新增或编辑)
const handleSubmit = async () => {
  isSaving.value = true;
  const data = { name: categoryName.value };

  try {
    if (currentCategoryId.value) {
      // 编辑
      await updateCategory(currentCategoryId.value, data);
      alert(`分类ID ${currentCategoryId.value} 更新成功！`);
    } else {
      // 新增
      await createCategory(data);
      alert('新分类创建成功！');
    }
    
    closeForm(); // 关闭表单
    fetchCategories(); // 刷新列表
  } catch (error) {
    console.error('提交分类失败:', error);
    alert('操作失败，请检查名称是否重复或网络连接。');
  } finally {
    isSaving.value = false;
  }
};

// 5. 处理删除操作
const handleDelete = async (id) => {
  if (confirm(`确定要删除 ID 为 ${id} 的分类吗？删除后该分类下的商品将失去分类信息。`)) {
    try {
      await deleteCategory(id);
      alert('分类删除成功！');
      fetchCategories(); // 刷新列表
    } catch (error) {
      console.error('删除分类失败:', error);
      alert('删除失败，请检查是否有商品关联该分类。');
    }
  }
};

onMounted(fetchCategories);
</script>

<style scoped>
/* 列表样式 (复用 ProductManagement 的样式理念) */
.category-management { padding: 20px; }
.header-actions { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.add-btn { background-color: #42b983; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; }

.table-container { background: white; border-radius: 8px; overflow-x: auto; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); }
table { width: 100%; border-collapse: collapse; }
th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
th { background-color: #f2f2f2; font-weight: bold; }
.actions button { margin-right: 8px; padding: 6px 10px; border-radius: 3px; cursor: pointer; }
.edit-btn { background-color: #3498db; color: white; border: none; }
.delete-btn { background-color: #e74c3c; color: white; border: none; }

/* 模态框样式 (与 ProductForm 类似，但内置到 CategoryManagement) */
.modal-overlay { /* 与 ProductForm.vue 中的模态框样式相同 */
  position: fixed; top: 0; left: 0; width: 100%; height: 100%; 
  background: rgba(0, 0, 0, 0.5); display: flex; justify-content: center; 
  align-items: center; z-index: 1000;
}
.modal-content {
  background: white; padding: 30px; border-radius: 8px; width: 400px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
.form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
.form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
.form-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
.submit-btn, .cancel-btn { padding: 10px 15px; border-radius: 4px; cursor: pointer; border: none; font-weight: bold; }
.submit-btn { background-color: #42b983; color: white; }
.cancel-btn { background-color: #e0e0e0; color: #333; }
</style>