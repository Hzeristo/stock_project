<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    :title="`${stock.name} (${stock.code})`"
    width="60%"
    class="stock-detail-dialog"
    :before-close="handleClose"
  >
    <div class="stock-detail-content">
      <div class="price-overview">
        <div class="current-price">
          <span class="price">{{ stock.currentPrice || '--' }}</span>
          <span :class="{'change-up': stock.change > 0, 'change-down': stock.change < 0}" class="change">
            {{ stock.change > 0 ? '+' : '' }}{{ stock.change }}%
          </span>
        </div>
        <div class="trading-date">
          {{ getTodayDate() }} 交易时段
        </div>
      </div>

      <el-descriptions class="info-table" title="基本信息" :column="2" border>
        <el-descriptions-item label="股票代码">{{ stock.code }}</el-descriptions-item>
        <el-descriptions-item label="股票名称">{{ stock.name }}</el-descriptions-item>
        <el-descriptions-item label="今日开盘价">{{ stock.openPrice || '--' }} 元</el-descriptions-item>
        <el-descriptions-item label="昨日收盘价">{{ stock.prevClosePrice || '--' }} 元</el-descriptions-item>
        <el-descriptions-item label="最高价">{{ stock.highPrice || '--' }} 元</el-descriptions-item>
        <el-descriptions-item label="最低价">{{ stock.lowPrice || '--' }} 元</el-descriptions-item>
        <el-descriptions-item label="成交量">{{ stock.volume || '--' }}</el-descriptions-item>
        <el-descriptions-item label="成交额">{{ stock.turnover || '--' }}</el-descriptions-item>
        <el-descriptions-item label="市盈率">{{ stock.pe || '--' }}</el-descriptions-item>
        <el-descriptions-item label="市净率">{{ stock.pb || '--' }}</el-descriptions-item>
      </el-descriptions>

      <div class="chart-section">
        <div class="section-header">
          <h3>股价走势</h3>
          <div class="time-selector">
            <el-radio-group v-model="timeRange" size="small">
              <el-radio-button label="day">日</el-radio-button>
              <el-radio-button label="week">周</el-radio-button>
              <el-radio-button label="month">月</el-radio-button>
              <el-radio-button label="year">年</el-radio-button>
            </el-radio-group>
          </div>
        </div>
        <div class="price-chart">
          <!-- 这里可以集成图表库，如ECharts -->
          <div class="chart-placeholder">
            <el-empty description="股价走势图 (可集成ECharts实现)"></el-empty>
          </div>
        </div>
      </div>

      <div class="action-section">
        <el-button type="primary" class="action-button trade-button">
          <el-icon><Sell /></el-icon>前往交易
        </el-button>
        <el-button class="action-button">
          <el-icon><Bell /></el-icon>设置提醒
        </el-button>
        <el-button class="action-button">
          <el-icon><Star /></el-icon>加入自选
        </el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import { ref } from 'vue';
import { Sell, Bell, Star } from '@element-plus/icons-vue';

export default {
  name: 'StockDetailDialog',
  components: {
    Sell,
    Bell,
    Star
  },
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    stock: {
      type: Object,
      required: true
    }
  },
  setup() {
    const timeRange = ref('day');

    const getTodayDate = () => {
      const today = new Date();
      const year = today.getFullYear();
      const month = String(today.getMonth() + 1).padStart(2, '0');
      const day = String(today.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    };

    return {
      timeRange,
      getTodayDate
    };
  },
  emits: ['update:visible', 'close'],
  methods: {
    handleClose() {
      this.$emit('update:visible', false);
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
.stock-detail-dialog :deep(.el-dialog__header) {
  padding: 16px 20px;
  margin: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  background-color: #f9f9f9;
}

.stock-detail-dialog :deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.stock-detail-dialog :deep(.el-dialog__body) {
  padding: 20px 24px;
}

.stock-detail-dialog :deep(.el-dialog__headerbtn) {
  top: 16px;
  right: 16px;
}

.stock-detail-content {
  padding: 0;
}

.price-overview {
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.current-price {
  display: flex;
  align-items: baseline;
  margin-bottom: 8px;
}

.price {
  font-size: 36px;
  font-weight: 700;
  margin-right: 12px;
}

.change {
  font-size: 18px;
  font-weight: 600;
  padding: 2px 8px;
  border-radius: 4px;
}

.change-up {
  color: #f56c6c;
  background-color: rgba(245, 108, 108, 0.1);
}

.change-down {
  color: #67c23a;
  background-color: rgba(103, 194, 58, 0.1);
}

.trading-date {
  font-size: 14px;
  color: #909399;
}

.info-table {
  margin-bottom: 30px;
}

.info-table :deep(.el-descriptions__title) {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 16px;
}

.info-table :deep(.el-descriptions__label) {
  font-weight: 500;
}

.chart-section {
  margin-top: 30px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.section-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
}

.time-selector {
  display: flex;
  align-items: center;
}

.price-chart {
  width: 100%;
  height: 300px;
  margin-bottom: 30px;
  border-radius: 8px;
  overflow: hidden;
}

.chart-placeholder {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.action-section {
  display: flex;
  gap: 12px;
  margin-top: 20px;
  justify-content: center;
}

.action-button {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 500;
  transition: transform 0.2s, box-shadow 0.2s;
}

.action-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.trade-button {
  background-color: #0071e3;
  border-color: #0071e3;
}

.trade-button:hover {
  background-color: #0077ed;
  border-color: #0077ed;
}

@media (max-width: 768px) {
  .stock-detail-dialog :deep(.el-dialog) {
    width: 92% !important;
  }
  
  .action-section {
    flex-direction: column;
    gap: 10px;
  }
  
  .price {
    font-size: 28px;
  }
  
  .change {
    font-size: 16px;
  }
}
</style>
