<template>
  <teleport to="body">    <el-dialog
      :model-value="visible"
      @update:model-value="$emit('update:visible', $event)"
      :title="stock && stock.name ? `${stock.name} (${stock.code})` : '股票详情'"
      :width="dialogWidth"
      class="stock-detail-dialog"
      :before-close="handleClose"
      append-to-body
      destroy-on-close
    >
    <div class="stock-detail-content">      <div class="price-overview">
        <div class="current-price">
          <span class="price">{{ stock && stock.currentPrice || '--' }}</span>
          <span v-if="stock && stock.change !== undefined" :class="{'change-up': stock.change > 0, 'change-down': stock.change < 0}" class="change">
            {{ stock.change > 0 ? '+' : '' }}{{ stock.change }}%
          </span>
          <span v-else class="change">--</span>
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
      </div>      <div class="action-section">
        <el-button type="primary" class="action-button trade-button" @click="goToTrading">
          <el-icon><Sell /></el-icon>前往交易
        </el-button>
        <el-button class="action-button" @click="showAlertDialog">
          <el-icon><Bell /></el-icon>设置提醒
        </el-button>
        <el-button 
          class="action-button" 
          :class="{ 'in-watchlist': isInWatchlist }"
          @click="toggleWatchlist"
        >
          <el-icon><Star /></el-icon>{{ isInWatchlist ? '移出自选' : '加入自选' }}
        </el-button>      </div>
    </div>

    <!-- 股票提醒弹窗 -->
    <stock-alert-dialog 
      v-model:visible="alertDialogVisible" 
      :stock="stock"
      @alert-added="handleAlertAdded"
    />
      </el-dialog>
  </teleport>
</template>

<script>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { Sell, Bell, Star } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import StockAlertDialog from './StockAlertDialog.vue';
import { addToWatchlist, removeFromWatchlist, isInWatchlist as checkInWatchlist } from '@/utils/stockManager';

export default {
  name: 'StockDetailDialog',
  components: {
    Sell,
    Bell,
    Star,
    StockAlertDialog
  },props: {
    visible: {
      type: Boolean,
      required: true,
      default: false
    },
    stock: {
      type: Object,
      required: true,
      default: () => ({})
    }
  },  setup(props, { emit }) {
    const router = useRouter();
    const timeRange = ref('day');
    const alertDialogVisible = ref(false);
    const isInWatchlist = ref(false);

    // 响应式弹窗宽度
    const dialogWidth = computed(() => {
      if (typeof window !== 'undefined') {
        const width = window.innerWidth;
        if (width <= 480) return '98%';
        if (width <= 768) return '95%';
        return '60%';
      }
      return '60%';
    });

    const getTodayDate = () => {
      const today = new Date();
      const year = today.getFullYear();
      const month = String(today.getMonth() + 1).padStart(2, '0');
      const day = String(today.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    };

    // 监听股票变化，更新自选状态
    watch(() => props.stock, (newStock) => {
      if (newStock && newStock.code) {
        isInWatchlist.value = checkInWatchlist(newStock.code);
      }
    }, { immediate: true });

    // 前往交易页面
    const goToTrading = () => {
      if (!props.stock || !props.stock.code) {
        ElMessage.warning('股票信息不完整');
        return;
      }

      // 关闭弹窗
      emit('update:visible', false);
      
      // 将股票信息存储到sessionStorage，供交易页面使用
      sessionStorage.setItem('selectedStockForTrade', JSON.stringify({
        code: props.stock.code,
        name: props.stock.name,
        currentPrice: props.stock.currentPrice
      }));

      // 跳转到交易页面
      router.push('/trading');
      ElMessage.success(`已跳转到交易页面，自动选择${props.stock.name}`);
    };

    // 显示提醒设置弹窗
    const showAlertDialog = () => {
      if (!props.stock || !props.stock.code) {
        ElMessage.warning('股票信息不完整');
        return;
      }
      alertDialogVisible.value = true;
    };

    // 切换自选股状态
    const toggleWatchlist = () => {
      if (!props.stock || !props.stock.code) {
        ElMessage.warning('股票信息不完整');
        return;
      }

      if (isInWatchlist.value) {
        // 移出自选
        const success = removeFromWatchlist(props.stock.code);
        if (success) {
          isInWatchlist.value = false;
          ElMessage.success(`已移出自选股: ${props.stock.name}`);
          emit('watchlist-changed', 'remove', props.stock);
        } else {
          ElMessage.error('移出自选股失败');
        }
      } else {
        // 添加到自选
        const success = addToWatchlist(props.stock);
        if (success) {
          isInWatchlist.value = true;
          ElMessage.success(`已添加到自选股: ${props.stock.name}`);
          emit('watchlist-changed', 'add', props.stock);
        } else {
          ElMessage.error('添加自选股失败');
        }
      }
    };

    // 处理提醒添加完成
    const handleAlertAdded = (alertData) => {
      ElMessage.success(`已为 ${props.stock.name} 设置价格提醒`);
      emit('alert-added', alertData);
    };

    return {
      timeRange,
      dialogWidth,
      alertDialogVisible,
      isInWatchlist,
      getTodayDate,
      goToTrading,
      showAlertDialog,
      toggleWatchlist,
      handleAlertAdded
    };
  },
  emits: ['update:visible', 'close', 'watchlist-changed', 'alert-added'],
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

.in-watchlist {
  background-color: #f56c6c;
  border-color: #f56c6c;
  color: white;
}

.in-watchlist:hover {
  background-color: #f78989;
  border-color: #f78989;
}

@media (max-width: 768px) {
  .stock-detail-dialog :deep(.el-dialog) {
    width: 95% !important;
    margin: 5vh auto !important;
  }

  .stock-detail-dialog :deep(.el-dialog__header) {
    padding: 12px 16px;
  }

  .stock-detail-dialog :deep(.el-dialog__title) {
    font-size: 16px;
  }

  .stock-detail-dialog :deep(.el-dialog__body) {
    padding: 16px 20px;
  }

  .price-overview {
    margin-bottom: 20px;
    padding-bottom: 12px;
  }

  .price {
    font-size: 28px;
  }

  .change {
    font-size: 16px;
    padding: 2px 6px;
  }

  .trading-date {
    font-size: 13px;
  }

  .info-table {
    margin-bottom: 24px;
  }

  .info-table :deep(.el-descriptions__title) {
    font-size: 15px;
    margin-bottom: 12px;
  }

  .info-table :deep(.el-descriptions) {
    --el-descriptions-table-border: 1px solid #dcdfe6;
    --el-descriptions-item-bordered-label-background: #fafafa;
  }

  .info-table :deep(.el-descriptions__label) {
    font-size: 13px;
    padding: 8px 12px;
  }

  .info-table :deep(.el-descriptions__content) {
    font-size: 13px;
    padding: 8px 12px;
  }

  .chart-section {
    margin-top: 24px;
  }

  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    margin-bottom: 12px;
  }

  .section-header h3 {
    font-size: 15px;
  }

  .price-chart {
    height: 250px;
    margin-bottom: 24px;
  }

  .action-section {
    flex-direction: column;
    gap: 10px;
    margin-top: 16px;
  }

  .action-button {
    width: 100%;
    justify-content: center;
    padding: 12px 20px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .stock-detail-dialog :deep(.el-dialog) {
    width: 98% !important;
    margin: 2vh auto !important;
    border-radius: 8px;
  }

  .stock-detail-dialog :deep(.el-dialog__header) {
    padding: 10px 12px;
  }

  .stock-detail-dialog :deep(.el-dialog__title) {
    font-size: 15px;
  }

  .stock-detail-dialog :deep(.el-dialog__body) {
    padding: 12px 16px;
  }

  .price-overview {
    margin-bottom: 16px;
    padding-bottom: 10px;
  }

  .current-price {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }

  .price {
    font-size: 24px;
    margin-right: 0;
  }

  .change {
    font-size: 14px;
    padding: 2px 5px;
  }

  .trading-date {
    font-size: 12px;
  }

  .info-table {
    margin-bottom: 20px;
  }

  .info-table :deep(.el-descriptions__title) {
    font-size: 14px;
    margin-bottom: 10px;
  }

  .info-table :deep(.el-descriptions__label) {
    font-size: 12px;
    padding: 6px 10px;
  }

  .info-table :deep(.el-descriptions__content) {
    font-size: 12px;
    padding: 6px 10px;
  }

  .chart-section {
    margin-top: 20px;
  }

  .section-header h3 {
    font-size: 14px;
  }

  .time-selector :deep(.el-radio-button__inner) {
    font-size: 12px;
    padding: 8px 12px;
  }

  .price-chart {
    height: 200px;
    margin-bottom: 20px;
  }

  .action-section {
    margin-top: 12px;
    gap: 8px;
  }

  .action-button {
    padding: 10px 16px;
    font-size: 13px;
  }
}
</style>
