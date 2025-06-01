<template>
  <div class="dashboard">
    <!-- 顶部导航标签 -->
    <div class="nav-tabs">
      <div class="tab-item active">
        <el-icon><Promotion /></el-icon> A股
      </div>
    </div>

    <!-- 指标卡片区域 -->
    <div class="metrics-panel">
      <div class="metric-card" style="background-color: #8a9a9e;">
        <div class="card-header">涨跌停对比</div>
        <div class="metric-content">
          <div class="metric-value">28 : 45</div>
        </div>
      </div>
      
      <div class="metric-card" style="background-color: #b19795;">
        <div class="card-header">昨日涨停表现</div>
        <div class="metric-content">
          <div class="metric-value">0.8%</div>
        </div>
      </div>
      
      <div class="metric-card" style="background-color: #d3c0ad;">
        <div class="card-header">大小盘对比</div>
        <div class="metric-content">
          <div class="metric-value">-0.9%~-1.4%</div>
          <div class="sub-text">今日走势</div>
        </div>
      </div>
    </div>

    <!-- 股票排行榜 -->
    <div class="stock-ranking-section">
      <div class="section-header">
        <h2>股票排行</h2>
      </div>

      <el-row :gutter="20" class="ranking-container">
        <!-- 涨幅榜 -->
        <el-col :span="8">
          <div class="ranking-card">
            <div class="ranking-title">涨幅榜</div>
            <div class="stock-list">
              <div v-for="(stock, index) in topGainers" :key="index" class="stock-item" @click="showStockDetail(stock)">
                <div class="stock-info">
                  <div class="stock-name">{{ stock.name }}</div>
                  <div class="stock-code">{{ stock.code }}</div>
                </div>
                <div class="stock-change stock-up">+{{ stock.change }}%</div>
              </div>
            </div>
          </div>
        </el-col>

        <!-- 跌幅榜 -->
        <el-col :span="8">
          <div class="ranking-card">
            <div class="ranking-title">跌幅榜</div>
            <div class="stock-list">
              <div v-for="(stock, index) in topLosers" :key="index" class="stock-item" @click="showStockDetail(stock)">
                <div class="stock-info">
                  <div class="stock-name">{{ stock.name }}</div>
                  <div class="stock-code">{{ stock.code }}</div>
                </div>
                <div class="stock-change stock-down">{{ stock.change }}%</div>
              </div>
            </div>
          </div>
        </el-col>

        <!-- 成交额榜 -->
        <el-col :span="8">
          <div class="ranking-card">
            <div class="ranking-title">成交额榜</div>
            <div class="stock-list">
              <div v-for="(stock, index) in topVolume" :key="index" class="stock-item" @click="showStockDetail({...stock, change: 0})">
                <div class="stock-info">
                  <div class="stock-name">{{ stock.name }}</div>
                  <div class="stock-code">{{ stock.code }}</div>
                </div>
                <div class="stock-volume">{{ stock.volume }}</div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>

      <!-- 最近收到的消息 -->
      <div class="messages-section">
        <div class="section-header">
          <h2>已推送消息</h2>
        </div>
        <div class="message-list">
          <div v-for="(message, index) in messages" :key="index" class="message-item">
            <el-icon color="#ff6b6a"><Bell /></el-icon>
            <div class="message-text">{{ message }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 股票详情弹窗 -->
    <stock-detail-dialog 
      v-model:visible="stockDialogVisible" 
      :stock="selectedStock" 
    />
  </div>
</template>

<script>
import { ref } from 'vue';
import { Bell, Promotion } from '@element-plus/icons-vue';
import StockDetailDialog from '@/components/stock/StockDetailDialog.vue';
import { ElMessage } from 'element-plus';

export default {
  name: 'DashboardView',
  components: {
    Bell,
    Promotion,
    StockDetailDialog
  },
  setup() {
    const topGainers = ref([
      { name: '阿里巴巴', code: '9988', change: 2.3 },
      { name: '腾讯控股', code: '0700', change: 1.8 },
      { name: '贵州茅台', code: '600519', change: 0.9 },
      { name: '中国平安', code: '601318', change: 0.7 },
      { name: '招商银行', code: '600036', change: 0.5 }
    ]);

    const topLosers = ref([
      { name: '京东集团', code: '9618', change: -1.8 },
      { name: '美团点评', code: '3690', change: -1.5 },
      { name: '小米集团', code: '1810', change: -1.2 },
      { name: '中国石油', code: '601857', change: -0.9 },
      { name: '中国工商银行', code: '601398', change: -0.6 }
    ]);

    const topVolume = ref([
      { name: '中国平安', code: '601318', volume: '5.23亿' },
      { name: '招商银行', code: '600036', volume: '4.86亿' },
      { name: '阿里巴巴', code: '9988', volume: '3.42亿' },
      { name: '腾讯控股', code: '0700', volume: '2.76亿' },
      { name: '贵州茅台', code: '600519', volume: '1.98亿' }
    ]);
    
    const messages = ref([
      'message1: 贵州茅台(600519)今日涨幅超过0.9%',
      'message2: 市场整体表现低迷，建议关注防御性板块',
      'message3: 近期成交量明显放大，可能存在反转信号',
      'message4: 中国平安(601318)发布2025年第一季度财报',
      'message5: 部分科技股估值进入合理区间，可逢低关注'
    ]);

    // 股票详情弹窗相关
    const stockDialogVisible = ref(false);
    const selectedStock = ref({});

    // 显示股票详情弹窗
    const showStockDetail = async (stock) => {
      try {
        // 在实际应用中，这里应该调用API获取更详细的股票数据
        // const { data } = await stockApi.getStockInfo(stock.code);
        // selectedStock.value = { ...stock, ...data };
        
        // 模拟获取的数据
        selectedStock.value = {
          ...stock,
          currentPrice: stock.change ? (100 + stock.change).toFixed(2) : '100.00',
          openPrice: '101.20',
          prevClosePrice: '100.00',
          highPrice: '102.50',
          lowPrice: '99.80',
          turnover: stock.volume || '1.58亿',
          pe: '18.5',
          pb: '2.3'
        };
        
        stockDialogVisible.value = true;
      } catch (error) {
        console.error('获取股票详情失败:', error);
        ElMessage.error('获取股票详情失败，请稍后再试');
      }
    };

    return {
      topGainers,
      topLosers,
      topVolume,
      messages,
      stockDialogVisible,
      selectedStock,
      showStockDetail
    };
  }
};
</script>

<style scoped>
.dashboard {
  padding: 28px;
  background-color: #f9fafc;
  min-height: 100%;
}

.nav-tabs {
  display: flex;
  margin-bottom: 24px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  padding-bottom: 6px;
}

.tab-item {
  padding: 12px 18px;
  margin-right: 28px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: all 0.2s ease;
  border-radius: 8px 8px 0 0;
}

.tab-item:hover {
  background-color: rgba(64, 158, 255, 0.05);
}

.tab-item.active {
  color: #409eff;
  border-bottom: 3px solid #409eff;
}

/* 指标卡片样式 */
.metrics-panel {
  display: flex;
  gap: 24px;
  margin-bottom: 32px;
}

.metric-card {
  flex: 1;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.04);
  color: rgba(255, 255, 255, 0.95);
  padding: 0;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  position: relative;
  z-index: 1;
}

.metric-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.1);
  opacity: 0;
  z-index: -1;
  transition: opacity 0.3s ease;
}

.metric-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.1);
}

.metric-card:hover::before {
  opacity: 1;
}

.card-header {
  padding: 18px;
  font-weight: 600;
  font-size: 17px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  letter-spacing: 0.4px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.metric-content {
  padding: 28px 18px;
  text-align: center;
}

.metric-value {
  font-size: 32px;
  font-weight: 700;
  line-height: 1.2;
  letter-spacing: 0.5px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
}

.sub-text {
  font-size: 14px;
  margin-top: 10px;
  opacity: 0.9;
  font-weight: 500;
}

/* 股票排行榜样式 */
.stock-ranking-section {
  margin-top: 32px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 22px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.04);
}

.section-header h2 {
  font-size: 22px;
  margin: 0;
  font-weight: 700;
  color: #303133;
  letter-spacing: 0.3px;
}

.compare-btn {
  font-weight: 600;
  border-radius: 8px;
  padding: 10px 16px;
  transition: all 0.2s ease;
}

.compare-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.ranking-container {
  margin-bottom: 36px;
}

.ranking-card {
  background-color: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.04);
  height: 100%;
  transition: all 0.3s ease;
}

.ranking-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  transform: translateY(-3px);
}

.ranking-title {
  padding: 18px;
  background-color: #fafbfc;
  font-weight: 700;
  font-size: 16px;
  border-bottom: 1px solid #f0f2f5;
  color: #303133;
  letter-spacing: 0.3px;
}

.stock-list {
  padding: 8px 0;
}

.stock-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  transition: background-color 0.2s ease;
  cursor: pointer;
}

.stock-item:hover {
  background-color: rgba(64, 158, 255, 0.05);
}

.stock-item:last-child {
  border-bottom: none;
}

.stock-info {
  display: flex;
  flex-direction: column;
}

.stock-name {
  font-weight: 600;
  margin-bottom: 4px;
  color: #303133;
  font-size: 15px;
}

.stock-code {
  font-size: 13px;
  color: #909399;
  font-weight: 500;
}

.stock-change {
  font-weight: 700;
  font-size: 16px;
  border-radius: 4px;
  padding: 4px 8px;
}

.stock-up {
  color: #f56c6c; /* 红色表示上涨 */
  background-color: rgba(245, 108, 108, 0.1);
}

.stock-down {
  color: #67c23a; /* 绿色表示下跌 */
  background-color: rgba(103, 194, 58, 0.1);
}

.stock-volume {
  font-weight: 600;
  color: #606266;
  background-color: #f5f7fa;
  padding: 4px 8px;
  border-radius: 4px;
}

/* 消息区域样式 */
.messages-section {
  margin-top: 36px;
}

.message-list {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.04);
  padding: 8px 0;
  transition: all 0.3s ease;
}

.message-list:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.message-item {
  padding: 16px 18px;
  display: flex;
  align-items: center;
  gap: 14px;
  border-bottom: 1px solid #f5f7fa;
  transition: background-color 0.2s ease;
}

.message-item:hover {
  background-color: rgba(0, 0, 0, 0.01);
}

.message-item:last-child {
  border-bottom: none;
}

.message-item .el-icon {
  background-color: rgba(255, 107, 106, 0.1);
  padding: 8px;
  border-radius: 50%;
}

.message-text {
  color: #303133;
  font-size: 14px;
  font-weight: 500;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .dashboard {
    padding: 16px 12px;
  }

  .nav-tabs {
    margin-bottom: 16px;
  }

  .tab-item {
    padding: 10px 12px;
    margin-right: 16px;
    font-size: 14px;
  }

  .metrics-panel {
    flex-direction: column;
    gap: 16px;
    margin-bottom: 24px;
  }

  .metric-card {
    border-radius: 12px;
  }

  .card-header {
    padding: 14px 16px;
    font-size: 15px;
  }

  .metric-content {
    padding: 20px 16px;
  }

  .metric-value {
    font-size: 24px;
  }

  .section-header h2 {
    font-size: 18px;
  }

  .ranking-container {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .ranking-card {
    margin-bottom: 0;
  }

  .ranking-title {
    padding: 14px 16px;
    font-size: 15px;
  }

  .stock-item {
    padding: 12px 16px;
  }

  .stock-name {
    font-size: 14px;
  }

  .stock-code {
    font-size: 12px;
  }

  .stock-change {
    font-size: 14px;
    padding: 3px 6px;
  }

  .message-item {
    padding: 14px 16px;
    gap: 12px;
  }

  .message-text {
    font-size: 13px;
  }

  .el-col {
    width: 100% !important;
    flex: 0 0 100%;
    max-width: 100%;
  }
}

@media (max-width: 480px) {
  .dashboard {
    padding: 12px 8px;
  }

  .nav-tabs {
    margin-bottom: 12px;
    padding-bottom: 4px;
  }

  .tab-item {
    padding: 8px 10px;
    margin-right: 12px;
    font-size: 13px;
    gap: 6px;
  }

  .metrics-panel {
    gap: 12px;
    margin-bottom: 20px;
  }

  .card-header {
    padding: 12px 14px;
    font-size: 14px;
  }

  .metric-content {
    padding: 16px 14px;
  }

  .metric-value {
    font-size: 20px;
  }

  .sub-text {
    font-size: 12px;
  }

  .section-header {
    margin-bottom: 16px;
  }

  .section-header h2 {
    font-size: 16px;
  }

  .ranking-container {
    gap: 12px;
  }

  .ranking-title {
    padding: 12px 14px;
    font-size: 14px;
  }

  .stock-item {
    padding: 10px 14px;
  }

  .stock-name {
    font-size: 13px;
    margin-bottom: 2px;
  }

  .stock-code {
    font-size: 11px;
  }

  .stock-change {
    font-size: 13px;
    padding: 2px 5px;
  }

  .stock-volume {
    font-size: 13px;
    padding: 2px 5px;
  }

  .messages-section {
    margin-top: 24px;
  }

  .message-item {
    padding: 12px 14px;
    gap: 10px;
  }

  .message-item .el-icon {
    padding: 6px;
  }

  .message-text {
    font-size: 12px;
    line-height: 1.3;
  }
}
</style>