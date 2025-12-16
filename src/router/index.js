import { createRouter, createWebHistory } from 'vue-router'
import AdminLogin from '@/views/admin/AdminLogin.vue';
import Home from '@/views/front/Home.vue' 
import { createPinia } from 'pinia';
import { useAuthStore } from '@/stores/auth';
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [],
})
// 3. 必须先实例化 Pinia，因为路由守卫在 Vue 应用挂载前执行
const pinia = createPinia();
const authStore = useAuthStore(pinia); // 实例化 Store

export default router

const routes = [
  // 前台首页
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/front/Home.vue')
  },
  // 后台登录页
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/admin/AdminLogin.vue')
  },
  // 后台管理面板
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/admin/AdminLayout.vue'),
    redirect: '/admin/dashboard', // 访问 /admin 自动重定向
    meta: { requiresAuth: true }, // 整个后台区域都需要权限
    children: [
      {
        path: 'dashboard', 
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'), 
      },
      // 商品管理路由 (引用了您刚刚创建的占位符文件)
      {
        path: 'products',
        name: 'ProductManagement',
        component: () => import('@/views/admin/ProductManagement.vue'), 
      },
      // 分类管理路由 (引用了您刚刚创建的占位符文件)
      {
        path: 'categories',
        name: 'CategoryManagement',
        component: () => import('@/views/admin/CategoryManagement.vue'), 
      },
    ]
  }
  
];

router.beforeEach((to, from, next) => {
  // 检查目标路由是否需要认证（我们使用 meta: { requiresAuth: true } 标记）
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  
  // 检查用户是否已登录
  const isLoggedIn = authStore.isLoggedIn;

  // 场景 1: 目标路由需要登录权限，但用户未登录
  if (requiresAuth && !isLoggedIn) {
    // 重定向到登录页
    next({ name: 'AdminLogin' });
  
  // 场景 2: 用户已登录，但尝试访问登录页
  } else if (isLoggedIn && to.name === 'AdminLogin') {
    // 阻止访问登录页，跳转到管理面板
    next({ name: 'AdminDashboard' });

  // 场景 3: 正常放行
  } else {
    next();
  }
});


