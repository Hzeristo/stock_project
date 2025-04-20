<template>
  <div class="header-bar">
    <div class="left-section">
      <el-input
        placeholder="Type here to search"
        v-model="searchText"
        class="search-input"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>
    <div class="right-section">
      <div class="datetime">
        <span>{{ currentDate }}</span>
        <span class="time">{{ currentTime }}</span>
      </div>
      <div class="notification">
        <el-badge :value="3" class="notification-badge">
          <el-icon size="24"><Bell /></el-icon>
        </el-badge>
      </div>
      <div class="user-info">
        <el-dropdown trigger="click">
          <div class="user-profile">
            <el-avatar size="small" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <div class="user-details">
              <span class="username">用户名</span>
              <span class="login-time">上次登录: {{ loginTime }}</span>
            </div>
            <el-icon><ArrowDown /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item icon="User">个人资料</el-dropdown-item>
              <el-dropdown-item icon="Setting">设置</el-dropdown-item>
              <el-dropdown-item divided icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';
import { Search, Bell, ArrowDown } from '@element-plus/icons-vue';

export default {
  name: 'HeaderBar',
  components: {
    Search,
    Bell,
    ArrowDown
  },
  setup() {
    const searchText = ref('');
    const currentDate = ref('');
    const currentTime = ref('');
    const loginTime = ref('2025-04-15 08:30');
    let timer = null;

    const updateDateTime = () => {
      const now = new Date();
      const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
      currentDate.value = now.toLocaleDateString('zh-CN', options);
      currentTime.value = now.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' });
    };

    onMounted(() => {
      updateDateTime();
      timer = setInterval(updateDateTime, 60000); // 每分钟更新一次
    });

    onUnmounted(() => {
      if (timer) {
        clearInterval(timer);
      }
    });

    return {
      searchText,
      currentDate,
      currentTime,
      loginTime
    };
  }
};
</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.left-section {
  flex: 1;
}

.search-input {
  max-width: 300px;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.datetime {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  font-size: 14px;
  color: #606266;
}

.time {
  font-weight: bold;
}

.notification-badge {
  cursor: pointer;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.user-details {
  display: flex;
  flex-direction: column;
  margin-right: 5px;
}

.username {
  font-size: 14px;
  font-weight: 500;
}

.login-time {
  font-size: 12px;
  color: #909399;
}
</style>