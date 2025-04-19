<template>
  <div class="main-layout">
    <div class="sidebar-container">
      <Sidebar />
    </div>
    <div class="main-container">
      <div class="header-container">
        <HeaderBar />
      </div>
      <div class="content-container">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from './Sidebar.vue';
import HeaderBar from './HeaderBar.vue';

export default {
  name: 'MainLayout',
  components: {
    Sidebar,
    HeaderBar
  }
}
</script>

<style scoped>
.main-layout {
  display: flex;
  height: 100vh;
  width: 100%;
  overflow: hidden;
}

.sidebar-container {
  width: 240px;
  height: 100%;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header-container {
  height: 60px;
  flex-shrink: 0;
}

.content-container {
  flex: 1;
  overflow-y: auto;
  background-color: #f5f7fa;
  padding: 16px;
}
</style>