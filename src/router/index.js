import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth';
import Detail from '@/views/front/Detail.vue'
const routes = [
  // 1. 根路径：显式处理跳转
  {
    path: '/',
    name: 'Root',
    redirect: to => {
      const token = localStorage.getItem('campus_user_token');
      // 如果有 token 就去后台，没 token 就去登录
      return token ? '/admin/dashboard' : '/admin/login';
    }
  },
  // 2. 前台首页
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/front/Home.vue'),
    meta: { requiresAuth: true } 
  },
  // 3. 商品详情页
  {
    path: '/detail/:id',
    name: 'ProductDetail',
    component: () => import('@/views/front/ProductDetail.vue')
  },
  // 4. 后台登录页
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/admin/AdminLogin.vue')
  },
  // 5. 后台管理面板
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/admin/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true }, 
    children: [
      {
        path: 'dashboard', 
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'), 
      },
      {
        path: 'add-product',
        name: 'AddProduct',
        component: () => import('@/views/admin/AddProduct.vue'), 
      },
      {
        path: 'products',
        name: 'ProductManagement',
        component: () => import('@/views/admin/ProductManagement.vue'), 
      },
      {
        path: 'categories',
        name: 'CategoryManagement',
        component: () => import('@/views/admin/CategoryManagement.vue'), 
      },
    ]

  },
  //6.点击卡片查看详情
  { path: '/detail/:id', 
    name: 'Detail',
    component:Detail 
  },
//7.
  {
  path: '/detail/:id',
  name: 'Detail',
  component: () => import('@/views/front/Detail.vue')
}
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  
  // 统一获取登录状态
  const token = localStorage.getItem('campus_user_token');
  const isLoggedIn = authStore.isLoggedIn || !!token;

  // 核心逻辑修改：
  // 1. 如果去的是根路径 "/"
  if (to.path === '/') {
    if (isLoggedIn) {
      next({ name: 'AdminDashboard' });
    } else {
      next({ name: 'AdminLogin' });
    }
    return; // 必须 return，否则会执行下面的 next() 导致重复
  }

  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  
  // 场景 A: 访问受限页面且未登录
  if (requiresAuth && !isLoggedIn) {
    next({ name: 'AdminLogin' });
  } 
  // 场景 B: 已登录用户尝试去登录页
  else if (to.name === 'AdminLogin' && isLoggedIn) {
    next({ name: 'AdminDashboard' });
  } 
  // 场景 C: 正常放行
  else {
    next();
  }
});

export default router;