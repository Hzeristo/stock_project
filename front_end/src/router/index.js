import { createRouter, createWebHistory } from 'vue-router';
import MainLayout from '@/components/layout/MainLayout.vue';
import Dashboard from '@/views/Dashboard.vue';

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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;