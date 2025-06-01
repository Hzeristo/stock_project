<template>
  <div class="account-page">
    <h1>我的账户</h1>
    
    <!-- 账户概览卡片 -->
    <el-card class="overview-card">
      <div class="account-overview">
        <div class="overview-item">
          <span class="label">总资产</span>
          <span class="value">¥ {{ formatNumber(accountInfo.availableFunds + accountInfo.stockValue) }}</span>
        </div>
        <div class="overview-item">
          <span class="label">可用资金</span>
          <span class="value">¥ {{ formatNumber(accountInfo.availableFunds) }}</span>
        </div>
        <div class="overview-item">
          <span class="label">持仓市值</span>
          <span class="value">¥ {{ formatNumber(accountInfo.stockValue) }}</span>
        </div>
      </div>
    </el-card>

    <!-- 功能区域 -->
    <el-tabs class="account-tabs" v-model="activeTab">
      <!-- 持仓管理 -->
      <el-tab-pane label="持仓管理" name="positions">
        <el-table :data="positions" style="width: 100%">
          <el-table-column prop="stockName" label="股票名称" align="center" />
          <el-table-column prop="stockCode" label="股票代码" align="center" />
          <el-table-column prop="quantity" label="持仓数量" align="center" />
          <el-table-column prop="avgPrice" label="平均成本" align="center" />
          <el-table-column prop="currentPrice" label="当前价格" align="center" />
          <el-table-column prop="profitLoss" label="盈亏" align="center">
            <template #default="scope">
              <span :class="scope.row.profitLoss >= 0 ? 'profit' : 'loss'">
                {{ scope.row.profitLoss >= 0 ? '+' : '' }}{{ scope.row.profitLoss }}%
              </span>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>      <!-- 交易记录 -->
      <el-tab-pane label="交易记录" name="transactions">
        <div class="transactions-header">
          <div class="transactions-title">
            <span>交易记录</span>
            <span class="record-count">（共 {{ transactions.length }} 条记录）</span>
          </div>
          <el-button type="primary" size="small" @click="refreshTransactions">
            <el-icon><Refresh /></el-icon>
            刷新记录
          </el-button>
        </div>
        <el-table :data="transactions" style="width: 100%">
          <el-table-column prop="date" label="交易日期" align="center" width="150" />
          <el-table-column prop="stockName" label="股票名称" align="center" />
          <el-table-column prop="stockCode" label="股票代码" align="center" width="120" />
          <el-table-column prop="type" label="交易类型" align="center" width="100">
            <template #default="scope">
              <span :class="scope.row.type === '买入' ? 'profit' : 'loss'">
                {{ scope.row.type }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="交易价格" align="center" />
          <el-table-column prop="quantity" label="交易数量" align="center" />
          <el-table-column prop="amount" label="交易金额" align="center" />
        </el-table>
      </el-tab-pane>

      <!-- 资金管理 -->
      <el-tab-pane label="资金管理" name="funds">
        <div class="fund-operations">
          <div class="fund-operation">
            <el-card class="fund-card">
              <template #header>
                <div class="card-header">
                  <span>充值</span>
                </div>
              </template>
              <el-form :model="depositForm" label-width="80px" class="fund-form">
                <el-form-item label="充值金额">
                  <el-input v-model="depositForm.amount" type="number" min="0.01" step="0.01">
                    <template #prefix>¥</template>
                  </el-input>
                </el-form-item>
                <el-form-item class="button-item">
                  <el-button type="primary" @click="handleDeposit">确认充值</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </div>
          <div class="fund-operation">
            <el-card class="fund-card">
              <template #header>
                <div class="card-header">
                  <span>提现</span>
                </div>
              </template>
              <el-form :model="withdrawForm" label-width="80px" class="fund-form">
                <el-form-item label="提现金额">
                  <el-input v-model="withdrawForm.amount" type="number" min="0.01" step="0.01">
                    <template #prefix>¥</template>
                  </el-input>
                </el-form-item>
                <el-form-item class="button-item">
                  <el-button type="warning" @click="handleWithdraw">确认提现</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Refresh } from '@element-plus/icons-vue'
import { getTradeRecords, initializeDefaultRecords } from '@/utils/tradeManager'

export default {
  name: 'AccountView',
  components: {
    Refresh
  },
  setup() {
    const activeTab = ref('positions')

    // 格式化数字
    const formatNumber = (num) => {
      return num.toLocaleString('zh-CN', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      })
    }
    
    // 账户信息
    const accountInfo = reactive({
      availableFunds: 50000.00,
      stockValue: 26251.00
    })

    // 持仓管理
    const positions = ref([
      {
        stockName: '阿里巴巴',
        stockCode: '9988.HK',
        quantity: 100,
        avgPrice: 88.50,
        currentPrice: 89.75,
        profitLoss: 1.41
      },
      {
        stockName: '腾讯控股',
        stockCode: '0700.HK',
        quantity: 30,
        avgPrice: 365.00,
        currentPrice: 368.20,
        profitLoss: 0.88
      },
      {
        stockName: '小米集团',
        stockCode: '1810.HK',
        quantity: 500,
        avgPrice: 12.80,
        currentPrice: 12.46,
        profitLoss: -2.66
      }
    ])    // 交易记录
    const transactions = ref([])
      // 组件挂载时加载交易记录
    onMounted(() => {
      // 初始化默认记录（如果没有记录的话）
      initializeDefaultRecords()
      // 从本地存储加载交易记录
      transactions.value = getTradeRecords()
    })

    // 刷新交易记录
    const refreshTransactions = () => {
      transactions.value = getTradeRecords()
      ElMessage.success('交易记录已刷新')
    }

    // 充值表单
    const depositForm = reactive({
      amount: ''
    })

    // 提现表单
    const withdrawForm = reactive({
      amount: ''
    })

    // 充值处理
    const handleDeposit = () => {
      if (!depositForm.amount) {
        ElMessage.warning('请输入充值金额')
        return
      }
      const amount = parseFloat(depositForm.amount.replace(',', ''))
      if (amount <= 0 || (amount * 100) % 1 > 0) {
        ElMessage.warning('请输入有效的充值金额')
        return
      }
      if (amount > 1000000000) {
        ElMessage.error('超过单次最高充值金额')
        return 
      }
      ElMessage.success('充值申请已提交')
      accountInfo.availableFunds += amount
      depositForm.amount = ''
    }

    // 提现处理
    const handleWithdraw = () => {
      if (!withdrawForm.amount) {
        ElMessage.warning('请输入提现金额')
        return
      }
      const amount = parseFloat(withdrawForm.amount.replace(',', ''))
      if (amount <= 0 || (amount * 100) % 1 > 0) {
        ElMessage.warning('请输入有效的提现金额')
        return
      }
      if (amount > accountInfo.availableFunds) {
        ElMessage.error('可用资金不足')
        return      }
      ElMessage.success('提现申请已提交')
      accountInfo.availableFunds -= amount
      withdrawForm.amount = ''
    }

    return {
      activeTab,
      formatNumber,
      accountInfo,
      positions,
      transactions,
      refreshTransactions,
      depositForm,
      withdrawForm,
      handleDeposit,
      handleWithdraw
    }
  }
}
</script>
<style scoped>
.account-page {
  padding: 20px;
}

.overview-card {
  margin-bottom: 20px;
}

.account-overview {
  display: flex;
  justify-content: space-around;
  padding: 20px 0;
}

.overview-item {
  text-align: center;
}

.overview-item .label {
  display: block;
  color: #909399;
  font-size: 14px;
  margin-bottom: 8px;
}

.overview-item .value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.account-tabs {
  margin-top: 20px;
}

.transactions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding: 0 4px;
}

.transactions-title {
  font-size: 16px;
  font-weight: 600;
}

.record-count {
  font-size: 14px;
  color: #909399;
  font-weight: normal;
  margin-left: 8px;
}

.fund-operations {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.fund-operation {
  flex: 1;
  display: flex;
  justify-content: center;
  width: 50%;
}

.fund-card {
  flex: 1;
  max-width: 400px;
}

.card-header {
  text-align: center;
}

.fund-form {
  padding: 20px;
}

.button-item {
  margin-bottom: 0;
  text-align: center;
}

.profit {
  color: #67c23a;
}

.loss {
  color: #f56c6c;
}

.el-table {
  margin-top: 20px;
}
</style>
