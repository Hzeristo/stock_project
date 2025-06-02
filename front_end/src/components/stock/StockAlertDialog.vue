<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    title="设置价格提醒"
    width="500px"
    class="alert-dialog"
    :before-close="handleClose"
  >
    <div class="alert-form-container">
      <div class="stock-info">
        <div class="stock-name">{{ stock.name || '--' }}</div>
        <div class="stock-code">{{ stock.code || '--' }}</div>
        <div class="current-price">
          当前价格: <span class="price">¥{{ stock.currentPrice || '--' }}</span>
        </div>
      </div>

      <el-form :model="alertForm" :rules="rules" ref="alertFormRef" label-width="120px">
        <el-form-item label="提醒类型" prop="type">
          <el-radio-group v-model="alertForm.type">
            <el-radio value="price">价格提醒</el-radio>
            <el-radio value="change">涨跌幅提醒</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="alertForm.type === 'price'" label="触发条件" prop="condition">
          <el-select v-model="alertForm.condition" placeholder="选择条件">
            <el-option label="价格高于" value="above" />
            <el-option label="价格低于" value="below" />
            <el-option label="价格等于" value="equal" />
          </el-select>
        </el-form-item>

        <el-form-item v-if="alertForm.type === 'change'" label="触发条件" prop="condition">
          <el-select v-model="alertForm.condition" placeholder="选择条件">
            <el-option label="涨幅超过" value="rise_above" />
            <el-option label="跌幅超过" value="fall_below" />
          </el-select>
        </el-form-item>

        <el-form-item 
          v-if="alertForm.type === 'price'" 
          label="目标价格" 
          prop="targetPrice"
        >
          <el-input-number
            v-model="alertForm.targetPrice"
            :min="0.01"
            :step="0.01"
            placeholder="请输入目标价格"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item 
          v-if="alertForm.type === 'change'" 
          label="目标百分比" 
          prop="targetPercent"
        >
          <el-input-number
            v-model="alertForm.targetPercent"
            :min="0.1"
            :step="0.1"
            placeholder="请输入百分比"
            style="width: 100%"
          />
          <span class="input-suffix">%</span>
        </el-form-item>

        <el-form-item label="提醒方式" prop="notifyMethod">
          <el-checkbox-group v-model="alertForm.notifyMethod">
            <el-checkbox value="popup">弹窗提醒</el-checkbox>
            <el-checkbox value="message">消息通知</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            v-model="alertForm.remark"
            type="textarea"
            :rows="2"
            placeholder="可选的备注信息"
            maxlength="100"
            show-word-limit
          />
        </el-form-item>
      </el-form>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="submitAlert">确认设置</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import { ref, reactive, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { addAlert } from '@/utils/stockManager'

export default {
  name: 'StockAlertDialog',
  props: {
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
  },
  emits: ['update:visible', 'close', 'alert-added'],
  setup(props, { emit }) {
    const alertFormRef = ref()
    
    const alertForm = reactive({
      type: 'price',
      condition: 'above',
      targetPrice: null,
      targetPercent: null,
      notifyMethod: ['popup'],
      remark: ''
    })

    const rules = {
      type: [
        { required: true, message: '请选择提醒类型', trigger: 'change' }
      ],
      condition: [
        { required: true, message: '请选择触发条件', trigger: 'change' }
      ],
      targetPrice: [
        { 
          required: true, 
          message: '请输入目标价格', 
          trigger: 'blur',
          validator: (rule, value, callback) => {
            if (alertForm.type === 'price' && (!value || value <= 0)) {
              callback(new Error('请输入有效的目标价格'))
            } else {
              callback()
            }
          }
        }
      ],
      targetPercent: [
        { 
          required: true, 
          message: '请输入目标百分比', 
          trigger: 'blur',
          validator: (rule, value, callback) => {
            if (alertForm.type === 'change' && (!value || value <= 0)) {
              callback(new Error('请输入有效的百分比'))
            } else {
              callback()
            }
          }
        }
      ],
      notifyMethod: [
        { 
          type: 'array', 
          required: true, 
          message: '请选择至少一种提醒方式', 
          trigger: 'change' 
        }
      ]
    }

    // 监听提醒类型变化，重置条件
    watch(() => alertForm.type, (newType) => {
      if (newType === 'price') {
        alertForm.condition = 'above'
        alertForm.targetPercent = null
      } else {
        alertForm.condition = 'rise_above'
        alertForm.targetPrice = null
      }
    })

    // 监听弹窗显示状态，重置表单
    watch(() => props.visible, (visible) => {
      if (visible) {
        resetForm()
        // 设置默认目标价格为当前价格
        nextTick(() => {
          if (props.stock.currentPrice) {
            alertForm.targetPrice = parseFloat(props.stock.currentPrice)
          }
        })
      }
    })

    const resetForm = () => {
      Object.assign(alertForm, {
        type: 'price',
        condition: 'above',
        targetPrice: null,
        targetPercent: null,
        notifyMethod: ['popup'],
        remark: ''
      })
      
      if (alertFormRef.value) {
        alertFormRef.value.clearValidate()
      }
    }

    const handleClose = () => {
      emit('update:visible', false)
      emit('close')
    }

    const submitAlert = async () => {
      if (!alertFormRef.value) return
      
      try {
        await alertFormRef.value.validate()
        
        const alertData = {
          stockCode: props.stock.code,
          stockName: props.stock.name,
          type: alertForm.type,
          condition: alertForm.condition,
          targetPrice: alertForm.type === 'price' ? alertForm.targetPrice : null,
          targetPercent: alertForm.type === 'change' ? alertForm.targetPercent : null,
          notifyMethod: alertForm.notifyMethod,
          remark: alertForm.remark,
          currentPrice: props.stock.currentPrice
        }
        
        const result = addAlert(alertData)
        
        if (result) {
          ElMessage.success('价格提醒设置成功')
          emit('alert-added', result)
          handleClose()
        } else {
          ElMessage.error('设置提醒失败，请稍后再试')
        }
      } catch (error) {
        console.error('表单验证失败:', error)
      }
    }

    return {
      alertForm,
      alertFormRef,
      rules,
      handleClose,
      submitAlert
    }
  }
}
</script>

<style scoped>
.alert-dialog :deep(.el-dialog__header) {
  padding: 16px 20px;
  margin: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  background-color: #f9f9f9;
}

.alert-dialog :deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.alert-dialog :deep(.el-dialog__body) {
  padding: 20px;
}

.alert-form-container {
  padding: 0;
}

.stock-info {
  background-color: #f8fafe;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 24px;
  border-left: 4px solid #409eff;
}

.stock-name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 4px;
}

.stock-code {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.current-price {
  font-size: 14px;
  color: #606266;
}

.current-price .price {
  font-size: 16px;
  font-weight: 600;
  color: #409eff;
}

.el-form-item {
  margin-bottom: 20px;
}

.input-suffix {
  margin-left: 8px;
  color: #909399;
  font-size: 14px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 移动端响应式 */
@media (max-width: 768px) {
  .alert-dialog :deep(.el-dialog) {
    width: 95% !important;
    margin: 5vh auto !important;
  }
  
  .alert-dialog :deep(.el-dialog__header) {
    padding: 12px 16px;
  }
  
  .alert-dialog :deep(.el-dialog__title) {
    font-size: 16px;
  }
  
  .alert-dialog :deep(.el-dialog__body) {
    padding: 16px;
  }
  
  .stock-info {
    padding: 12px;
    margin-bottom: 20px;
  }
  
  .stock-name {
    font-size: 16px;
  }
  
  .el-form-item {
    margin-bottom: 16px;
  }
  
  .el-form-item :deep(.el-form-item__label) {
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .alert-dialog :deep(.el-dialog) {
    width: 98% !important;
    margin: 2vh auto !important;
  }
  
  .alert-dialog :deep(.el-dialog__header) {
    padding: 10px 12px;
  }
  
  .alert-dialog :deep(.el-dialog__title) {
    font-size: 15px;
  }
  
  .alert-dialog :deep(.el-dialog__body) {
    padding: 12px;
  }
  
  .stock-info {
    padding: 10px;
    margin-bottom: 16px;
  }
  
  .stock-name {
    font-size: 15px;
  }
  
  .current-price .price {
    font-size: 15px;
  }
  
  .el-form-item {
    margin-bottom: 14px;
  }
  
  .el-form-item :deep(.el-form-item__label) {
    font-size: 13px;
    width: 100px !important;
  }
}
</style>
