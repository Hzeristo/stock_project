<template>
  <div class="dashboard">
    <div class="metrics-panel">
      <el-card class="metric-card">
        <template #header>
          <div class="card-header">
            <span>当前股票比较比例</span>
            <el-tag type="info" size="small">实时数据</el-tag>
          </div>
        </template>
        <div class="metric-value primary">28:45</div>
      </el-card>
      <el-card class="metric-card">
        <template #header>
          <div class="card-header">
            <span>昨日股票表现</span>
          </div>
        </template>
        <div class="metric-value success">+0.8%</div>
      </el-card>
      <el-card class="metric-card">
        <template #header>
          <div class="card-header">
            <span>大小盘股票比较</span>
          </div>
        </template>
        <div class="metric-value danger">-0.9% ~ -1.4%</div>
      </el-card>
    </div>
    
    <div class="stock-ranking-section">
      <el-card class="stock-ranking-card">
        <template #header>
          <div class="card-header">
            <span>股票排行榜</span>
            <div>
              <el-button type="primary" size="small">涨跌等待比</el-button>
            </div>
          </div>
        </template>
        <el-table :data="stockRanking" style="width: 100%" stripe>
          <el-table-column prop="rank" label="排名" width="80" />
          <el-table-column prop="name" label="股票名称" />
          <el-table-column prop="code" label="股票代码" />
          <el-table-column prop="current" label="当前价格" />
          <el-table-column prop="change" label="涨跌幅">
            <template #default="scope">
              <span :class="scope.row.change > 0 ? 'text-success' : 'text-danger'">
                {{ scope.row.change > 0 ? '+' : '' }}{{ scope.row.change }}%
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="volume" label="成交量" />
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'DashboardView',
  setup() {
    const stockRanking = ref([
      {
        rank: 1,
        name: '阿里巴巴',
        code: '9988.HK',
        current: '89.75',
        change: 2.3,
        volume: '3.42M'
      },
      {
        rank: 2,
        name: '腾讯控股',
        code: '0700.HK',
        current: '368.20',
        change: 1.8,
        volume: '2.76M'
      },
      {
        rank: 3,
        name: '贵州茅台',
        code: '600519.SH',
        current: '1732.50',
        change: 0.9,
        volume: '867.3K'
      },
      {
        rank: 4,
        name: '中国平安',
        code: '601318.SH',
        current: '46.80',
        change: -0.5,
        volume: '5.23M'
      },
      {
        rank: 5,
        name: '招商银行',
        code: '600036.SH',
        current: '37.25',
        change: -1.2,
        volume: '4.86M'
      }
    ]);

    return {
      stockRanking
    };
  }
};
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.metrics-panel {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.metric-card {
  flex: 1;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.metric-value {
  font-size: 28px;
  font-weight: 600;
  text-align: center;
  padding: 20px 0;
}

.primary {
  color: #409eff;
}

.success {
  color: #67c23a;
}

.danger {
  color: #f56c6c;
}

.stock-ranking-section {
  margin-top: 20px;
}

.stock-ranking-card {
  width: 100%;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.text-success {
  color: #67c23a;
}

.text-danger {
  color: #f56c6c;
}
</style>