import axios from 'axios';

// 创建axios实例
const api = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL || '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器 - 添加token等认证信息
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器 - 处理常见错误
api.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    if (error.response) {
      // 401错误 - 未授权，需要登录
      if (error.response.status === 401) {
        // 清除token并跳转到登录页
        localStorage.removeItem('token');
        window.location.href = '/login';
      }
      // 可以添加其他错误处理逻辑
    }
    return Promise.reject(error);
  }
);

export default api;