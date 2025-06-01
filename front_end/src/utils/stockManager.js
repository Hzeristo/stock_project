/**
 * 自选股和股票提醒管理工具
 * 用于管理用户的自选股列表和价格提醒
 */

const WATCHLIST_KEY = 'stock_watchlist'
const ALERTS_KEY = 'stock_alerts'

/**
 * 获取自选股列表
 */
export function getWatchlist() {
  try {
    const watchlist = localStorage.getItem(WATCHLIST_KEY)
    return watchlist ? JSON.parse(watchlist) : []
  } catch (error) {
    console.error('获取自选股列表失败:', error)
    return []
  }
}

/**
 * 添加股票到自选
 */
export function addToWatchlist(stock) {
  try {
    const watchlist = getWatchlist()
    
    // 检查是否已存在
    const existingIndex = watchlist.findIndex(item => item.code === stock.code)
    if (existingIndex !== -1) {
      // 已存在则更新信息
      watchlist[existingIndex] = {
        ...watchlist[existingIndex],
        ...stock,
        addTime: watchlist[existingIndex].addTime // 保持原添加时间
      }
    } else {
      // 新增
      const newStock = {
        ...stock,
        addTime: new Date().getTime(),
        id: Date.now() + Math.random()
      }
      watchlist.unshift(newStock)
    }
    
    localStorage.setItem(WATCHLIST_KEY, JSON.stringify(watchlist))
    return true
  } catch (error) {
    console.error('添加自选股失败:', error)
    return false
  }
}

/**
 * 从自选中移除股票
 */
export function removeFromWatchlist(stockCode) {
  try {
    const watchlist = getWatchlist()
    const filteredList = watchlist.filter(item => item.code !== stockCode)
    localStorage.setItem(WATCHLIST_KEY, JSON.stringify(filteredList))
    return true
  } catch (error) {
    console.error('移除自选股失败:', error)
    return false
  }
}

/**
 * 检查股票是否在自选中
 */
export function isInWatchlist(stockCode) {
  const watchlist = getWatchlist()
  return watchlist.some(item => item.code === stockCode)
}

/**
 * 获取股票提醒列表
 */
export function getAlerts() {
  try {
    const alerts = localStorage.getItem(ALERTS_KEY)
    return alerts ? JSON.parse(alerts) : []
  } catch (error) {
    console.error('获取提醒列表失败:', error)
    return []
  }
}

/**
 * 添加股票提醒
 */
export function addAlert(alert) {
  try {
    const alerts = getAlerts()
    const newAlert = {
      ...alert,
      id: Date.now() + Math.random(),
      createTime: new Date().getTime(),
      isActive: true,
      triggered: false
    }
    
    alerts.unshift(newAlert)
    
    // 限制提醒数量
    if (alerts.length > 100) {
      alerts.splice(100)
    }
    
    localStorage.setItem(ALERTS_KEY, JSON.stringify(alerts))
    return newAlert
  } catch (error) {
    console.error('添加提醒失败:', error)
    return null
  }
}

/**
 * 移除提醒
 */
export function removeAlert(alertId) {
  try {
    const alerts = getAlerts()
    const filteredAlerts = alerts.filter(alert => alert.id !== alertId)
    localStorage.setItem(ALERTS_KEY, JSON.stringify(filteredAlerts))
    return true
  } catch (error) {
    console.error('移除提醒失败:', error)
    return false
  }
}

/**
 * 更新提醒状态
 */
export function updateAlert(alertId, updates) {
  try {
    const alerts = getAlerts()
    const alertIndex = alerts.findIndex(alert => alert.id === alertId)
    
    if (alertIndex !== -1) {
      alerts[alertIndex] = { ...alerts[alertIndex], ...updates }
      localStorage.setItem(ALERTS_KEY, JSON.stringify(alerts))
      return true
    }
    return false
  } catch (error) {
    console.error('更新提醒失败:', error)
    return false
  }
}

/**
 * 获取股票的活跃提醒
 */
export function getStockAlerts(stockCode) {
  const alerts = getAlerts()
  return alerts.filter(alert => 
    alert.stockCode === stockCode && 
    alert.isActive && 
    !alert.triggered
  )
}

/**
 * 模拟检查价格提醒
 * 在实际应用中，这应该由后端定时任务完成
 */
export function checkPriceAlerts(stockData) {
  try {
    const alerts = getAlerts()
    const triggeredAlerts = []
    
    alerts.forEach(alert => {
      if (!alert.isActive || alert.triggered || alert.stockCode !== stockData.code) {
        return
      }
      
      const currentPrice = parseFloat(stockData.currentPrice)
      const targetPrice = parseFloat(alert.targetPrice)
      
      let shouldTrigger = false
      
      switch (alert.condition) {
        case 'above':
          shouldTrigger = currentPrice >= targetPrice
          break
        case 'below':
          shouldTrigger = currentPrice <= targetPrice
          break
        case 'equal':
          shouldTrigger = Math.abs(currentPrice - targetPrice) < 0.01
          break
      }
      
      if (shouldTrigger) {
        alert.triggered = true
        alert.triggerTime = new Date().getTime()
        alert.triggerPrice = currentPrice
        triggeredAlerts.push(alert)
      }
    })
    
    if (triggeredAlerts.length > 0) {
      localStorage.setItem(ALERTS_KEY, JSON.stringify(alerts))
    }
    
    return triggeredAlerts
  } catch (error) {
    console.error('检查价格提醒失败:', error)
    return []
  }
}

/**
 * 清空所有数据
 */
export function clearAllData() {
  try {
    localStorage.removeItem(WATCHLIST_KEY)
    localStorage.removeItem(ALERTS_KEY)
  } catch (error) {
    console.error('清空数据失败:', error)
  }
}

/**
 * 初始化默认自选股（仅在没有数据时执行）
 */
export function initializeDefaultWatchlist() {
  const existingWatchlist = getWatchlist()
  if (existingWatchlist.length === 0) {
    const defaultWatchlist = [
      {
        id: 1,
        code: '9988.HK',
        name: '阿里巴巴',
        currentPrice: '88.50',
        change: 2.3,
        addTime: new Date().getTime()
      },
      {
        id: 2,
        code: '0700.HK',
        name: '腾讯控股',
        currentPrice: '370.00',
        change: 1.8,
        addTime: new Date().getTime()
      }
    ]
    
    localStorage.setItem(WATCHLIST_KEY, JSON.stringify(defaultWatchlist))
  }
}
