<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div class="login-page">
      <div class="login-container">
        <!-- 左边图片 -->
        <div class="login-image">
          <img src="@/assets/login-side.jpg" alt="Login Illustration" />
        </div>
  
        <!-- 右边表单 -->
        <div class="login-form">
          <h2>密码登录</h2>
          <el-form :model="form" class="form">
            <el-form-item>
              <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
            </el-form-item>
            <el-form-item>
              <el-input 
              v-model="form.password" 
              :type="showword ? 'text':'password'" 
              size="large"
              placeholder="请输入密码" 
              >
              <template #suffix>
                <el-icon @click = "showword = !showword" class="cursor-pointer" >
                  <component :is="showword ? 'View' : 'Hide'" />
                </el-icon>
              </template>
            </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" class="login-btn" block>
                登录
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </template>
  
  
  <script>
  import { ref } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { View, Hide } from '@element-plus/icons-vue'
  import { ElMessage } from 'element-plus'
  export default {
    name: 'LoginView',
    components: {
      View,
      Hide
    },
    setup() {
      const router = useRouter()
      const route = useRoute()
  
      const form = ref({
        username: '',
        password: ''
      })
      const showword = ref(false);
  
      function handleLogin() {
        // 简单示例：假设用户名密码为 admin / 123456
        if (form.value.username === 'admin' && form.value.password === '123456') {
          sessionStorage.setItem('isLoggedIn', 'true')
          const redirect = route.query.redirect || '/dashboard'
          router.push(redirect)
        } else {
          ElMessage.error('账号或密码错误')
        }
      }
  
      return {
        form,
        showword,
        handleLogin
      }
    }
  }
  </script>

  <style scoped>
  .login-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f7fa;
  }
  
  .login-container {
    display: flex;
    width: 1000px;
    height: 500px;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
    overflow: hidden;
  }
  
  .login-image {
    flex: 1.5;
    background-color: #f0f0f0;
  }
  
  .login-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .login-form {
    flex: 1;
    padding: 50px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center; 
  }
  
  .login-form h2 {
    margin-bottom: 50px;
    font-size: 30px;
    color: #333;
  }
  
  .form {
    width: 90%;
    justify-content: center;
  }
  
  .login-btn {
    width: 100%;
  }

  .cursor-pointer {
    cursor: pointer;
  }
  </style>
  