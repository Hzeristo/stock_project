import { createRouter, createWebHistory } from 'vue-router';
import MainLayout from '@/components/layout/MainLayout.vue';
import Dashboard from '@/views/Dashboard.vue';
import Login from '@/views/Login.vue';

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: { title: '系统首页' }
      },
      {
        path: '/trading',
        name: 'Trading',
        component: () => import('@/views/Trading.vue'),
        meta: { title: '交易' }
      },
      {
        path: '/account',
        name: 'Account',
        component: () => import('@/views/Account.vue'),
        meta: { title: '我的账户' }
      },
      {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue'),
        meta: { title: '个人信息' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { title: '登录' }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  // Check if the user is authenticated
  const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';
  if(to.path === '/profile' && !isLoggedIn||
     to.path === '/trading' && !isLoggedIn||
     to.path === '/account' && !isLoggedIn){
    // Redirect to login if not authenticated
    next({ path: '/login',query: { redirect: to.fullPath } });
  }else{
    next();
  }
});

export default router;