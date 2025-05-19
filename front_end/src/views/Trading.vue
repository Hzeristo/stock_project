<template>
  <div class="trading-container">
    <!-- 待匹配交易表格展示 -->
    <el-card class="pending-trade-card">
      <template #header>
        <div class="card-header">
          <span>待匹配交易</span>
        </div>
      </template>
      <el-table :data="pendingTrades" stripe style="width: 100%">
        <el-table-column prop="stockCode" label="股票代码" width="180" />
        <el-table-column prop="type" label="交易类型" width="120" />
        <el-table-column prop="price" label="单价（¥）" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="totalPrice" label="总价（¥）" />
        <el-table-column prop="time" label="提交时间" />
      </el-table>
    </el-card>

    <!-- 交易操作表单 -->
    <el-card class="trading-card">
      <template #header>
        <div class="card-header">
          <span>股票交易操作</span>
        </div>
      </template>

      <el-form :model="tradeForm" label-width="100px" class="trading-form">
        <el-form-item label="股票代码">
          <el-select v-model="tradeForm.stockCode" placeholder="选择股票" style="width: 240px">
            <el-option
              v-for="stock in availableStocks"
              :key="stock.code"
              :label="`${stock.name}(${stock.code})`"
              :value="stock.code"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="交易类型">
          <el-radio-group v-model="tradeForm.type">
            <el-radio-button label="买入" />
            <el-radio-button label="卖出" />
          </el-radio-group>
        </el-form-item>

        <el-form-item label="单价（¥）">
          <el-input-number v-model="tradeForm.price" :min="0.01" :step="0.01" style="width: 240px" />
        </el-form-item>

        <el-form-item label="数量">
          <el-input-number v-model="tradeForm.quantity" :min="1" style="width: 240px" />
        </el-form-item>

        <el-form-item label="总价（¥）">
          <div class="total-price">{{ totalPrice }}</div>
        </el-form-item>

        <el-form-item label="资金密码">
          <el-input
            v-model="tradeForm.password"
            type="password"
            show-password
            placeholder="请输入资金账户密码"
            style="width: 240px"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitTrade">提交交易</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'

export default {
  name: 'TradingView',
  setup() {
    const tradeForm = ref({
      stockCode: '',
      type: '买入',
      price: 0,
      quantity: 0,
      password: ''
    })

    const availableStocks = ref([
      { name: '阿里巴巴', code: '9988.HK' },
      { name: '腾讯控股', code: '0700.HK' },
      { name: '贵州茅台', code: '600519.SH' },
      { name: '中国平安', code: '601318.SH' }
    ])

    const pendingTrades = ref([]) // 待匹配交易记录

    const totalPrice = computed(() => {
      const p = Number(tradeForm.value.price)
      const q = Number(tradeForm.value.quantity)
      return isNaN(p * q) ? '0.00' : (p * q).toFixed(2)
    })

    const submitTrade = () => {
      const { stockCode, price, quantity, password } = tradeForm.value
      if (!stockCode || !price || !quantity || !password) {
        ElMessage.warning('请填写完整交易信息')
        return
      }
      if ((price * 100) % 1 > 0) {
        ElMessage.warning('请输入有效的单价')
        return
      }
      if (quantity % 1 > 0) {
        ElMessage.warning('请输入有效的数量')
        return
      }
      if (password !== '123456') {
        ElMessage.error('资金账户密码错误')
        return
      }
      
      // 更新待匹配交易记录
      const pendingTransaction = {
        stockCode,
        type: tradeForm.value.type,
        price: price.toFixed(2),
        quantity,
        totalPrice: totalPrice.value,
        time: new Date().toLocaleString()
      }
      pendingTrades.value.unshift(pendingTransaction)

      ElMessage.success('成功提交订单')

      // 清空表单数据
      resetForm()
    }

    const resetForm = () => {
      tradeForm.value = {
        stockCode: '',
        type: '买入',
        price: 0,
        quantity: 0,
        password: ''
      }
    }

    return {
      tradeForm,
      availableStocks,
      pendingTrades,
      totalPrice,
      submitTrade,
      resetForm
    }
  }
}
</script>

<style scoped>
.trading-container {
  padding: 30px;
}

.trading-card {
  max-width: 800px;
  margin: 20px auto;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.pending-trade-card {
  margin-bottom: 40px;
  max-width: 1200px; /* 增加待匹配交易表格的宽度 */
  margin: 0 auto;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  font-size: 20px;
  font-weight: bold;
}

.trading-form {
  padding: 20px;

  /* 表单整体纵向布局 + 居中 */
  display: flex;
  flex-direction: column;
  align-items: center;
}

.total-price {
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
  line-height: 32px;
}

.el-table .cell {
  font-size: 14px;
}


.el-form-item {
  width: 100%;
  max-width: 400px; /* 控制每项的宽度 */
  justify-content: center;
}
</style>


