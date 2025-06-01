/**
 * 股票数据模拟服务
 * 提供相对稳定的股票数据，避免每次刷新都随机变化
 */

// 固定的股票基础数据
const STOCK_BASE_DATA = {
  '600519': { 
    name: '贵州茅台', 
    basePrice: 1580.00,
    sector: '白酒',
    market: 'SH'
  },
  '000001': { 
    name: '平安银行', 
    basePrice: 12.50,
    sector: '银行',
    market: 'SZ'
  },
  '600036': { 
    name: '招商银行', 
    basePrice: 35.80,
    sector: '银行',
    market: 'SH'
  },
  '601318': { 
    name: '中国平安', 
    basePrice: 45.20,
    sector: '保险',
    market: 'SH'
  },
  '000002': { 
    name: '万科A', 
    basePrice: 16.30,
    sector: '地产',
    market: 'SZ'
  },
  '601857': { 
    name: '中国石油', 
    basePrice: 7.20,
    sector: '石油',
    market: 'SH'
  },
  '9988': { 
    name: '阿里巴巴', 
    basePrice: 76.50,
    sector: '互联网',
    market: 'HK'
  },
  '0700': { 
    name: '腾讯控股', 
    basePrice: 315.20,
    sector: '互联网',
    market: 'HK'
  },
  '601398': { 
    name: '中国工商银行', 
    basePrice: 4.85,
    sector: '银行',
    market: 'SH'
  },
  '601939': { 
    name: '中国建设银行', 
    basePrice: 6.12,
    sector: '银行',
    market: 'SH'
  },  '600000': { 
    name: '浦发银行', 
    basePrice: 8.45,
    sector: '银行',
    market: 'SH'
  },
  '000858': { 
    name: '五粮液', 
    basePrice: 158.60,
    sector: '白酒',
    market: 'SZ'
  },
  '002415': { 
    name: '海康威视', 
    basePrice: 28.90,
    sector: '电子',
    market: 'SZ'
  },
  '600276': { 
    name: '恒瑞医药', 
    basePrice: 42.80,
    sector: '医药',
    market: 'SH'
  },
  '300059': { 
    name: '东方财富', 
    basePrice: 15.80,
    sector: '金融',
    market: 'SZ'
  },
  '002142': { 
    name: '宁波银行', 
    basePrice: 32.40,
    sector: '银行',
    market: 'SZ'
  },
  '600690': { 
    name: '海尔智家', 
    basePrice: 23.50,
    sector: '家电',
    market: 'SH'
  },
  '601012': { 
    name: '隆基绿能', 
    basePrice: 18.90,
    sector: '新能源',
    market: 'SH'
  },
  '300760': { 
    name: '迈瑞医疗', 
    basePrice: 268.50,
    sector: '医疗器械',
    market: 'SZ'
  },
  '002594': { 
    name: 'BYD', 
    basePrice: 214.80,
    sector: '新能源汽车',
    market: 'SZ'
  },
  '300124': { 
    name: '汇川技术', 
    basePrice: 58.20,
    sector: '工业自动化',
    market: 'SZ'
  },
  '600309': { 
    name: '万华化学', 
    basePrice: 78.60,
    sector: '化工',
    market: 'SH'
  },
  '000568': { 
    name: '泸州老窖', 
    basePrice: 98.70,
    sector: '白酒',
    market: 'SZ'
  },
  '002304': { 
    name: '洋河股份', 
    basePrice: 89.40,
    sector: '白酒',
    market: 'SZ'
  }
}

/**
 * 生成基于时间的稳定价格数据
 * 使用日期作为种子，确保同一天内价格相对稳定
 */
function generateStablePrice(stockCode, basePrice) {
  const today = new Date().toDateString()
  const seed = hashString(today + stockCode)
  
  // 使用种子生成-3%到+3%的变化
  const changePercent = (seed % 600 - 300) / 100 // -3.00 到 +3.00
  const currentPrice = basePrice * (1 + changePercent / 100)
  
  return {
    currentPrice: currentPrice.toFixed(2),
    change: changePercent.toFixed(2),
    changeAmount: (currentPrice - basePrice).toFixed(2)
  }
}

/**
 * 简单的字符串哈希函数
 */
function hashString(str) {
  let hash = 0
  for (let i = 0; i < str.length; i++) {
    const char = str.charCodeAt(i)
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }
  return Math.abs(hash)
}

/**
 * 获取股票的实时数据（模拟）
 */
export function getStockRealtimeData(stockCode) {
  const baseData = STOCK_BASE_DATA[stockCode]
  if (!baseData) {
    return {
      code: stockCode,
      name: `股票${stockCode}`,
      currentPrice: '0.00',
      change: '0.00',
      changeAmount: '0.00',
      market: 'Unknown'
    }
  }
  
  const priceData = generateStablePrice(stockCode, baseData.basePrice)
  
  return {
    code: stockCode,
    name: baseData.name,
    sector: baseData.sector,
    market: baseData.market,
    ...priceData,
    openPrice: (parseFloat(priceData.currentPrice) * (1 + Math.random() * 0.02 - 0.01)).toFixed(2),
    highPrice: (parseFloat(priceData.currentPrice) * (1 + Math.random() * 0.03)).toFixed(2),
    lowPrice: (parseFloat(priceData.currentPrice) * (1 - Math.random() * 0.03)).toFixed(2),
    volume: generateVolume(stockCode),
    turnover: generateTurnover(stockCode)
  }
}

/**
 * 生成成交量（基于股票代码的稳定值）
 */
function generateVolume(stockCode) {
  const seed = hashString(stockCode)
  const volume = (seed % 10000 + 1000) * 100 // 10万到100万手
  return (volume / 10000).toFixed(1) + '万手'
}

/**
 * 生成成交额（基于股票代码的稳定值）
 */
function generateTurnover(stockCode) {
  const seed = hashString(stockCode + 'turnover')
  const turnover = (seed % 50 + 10) / 10 // 1.0到6.0亿
  return turnover.toFixed(1) + '亿'
}

/**
 * 批量获取多只股票的实时数据
 */
export function getBatchStockData(stockCodes) {
  return stockCodes.map(code => getStockRealtimeData(code))
}

/**
 * 更新自选股的价格信息
 */
export function updateWatchlistPrices(watchlist) {
  return watchlist.map(stock => {
    const realtimeData = getStockRealtimeData(stock.code)
    return {
      ...stock,
      ...realtimeData
    }
  })
}

/**
 * 检查是否需要发送价格提醒
 */
export function checkPriceAlerts(stockCode, currentPrice, alerts) {
  return alerts.filter(alert => {
    if (alert.stockCode !== stockCode || !alert.enabled) {
      return false
    }
    
    const price = parseFloat(currentPrice)
    const targetPrice = parseFloat(alert.targetPrice)
    
    switch (alert.condition) {
      case 'above':
        return price >= targetPrice
      case 'below':
        return price <= targetPrice
      case 'change_up':
        // 这里需要获取昨日收盘价进行比较
        return false // 暂时不实现
      case 'change_down':
        return false // 暂时不实现
      default:
        return false
    }
  })
}

/**
 * 生成股票排行榜数据
 */
export function generateStockRankingData() {
  const allStocks = Object.keys(STOCK_BASE_DATA).map(code => {
    const stockInfo = STOCK_BASE_DATA[code]
    const priceData = generateStablePrice(code, stockInfo.basePrice)
    return {
      code,
      name: stockInfo.name,
      sector: stockInfo.sector,
      market: stockInfo.market,
      basePrice: stockInfo.basePrice,
      currentPrice: parseFloat(priceData.currentPrice),
      change: parseFloat(priceData.change),
      changeAmount: parseFloat(priceData.changeAmount),
      volume: generateTradingVolume(code, stockInfo.basePrice)
    }
  })

  // 按涨跌幅排序获取涨幅榜
  const topGainers = allStocks
    .filter(stock => stock.change > 0)
    .sort((a, b) => b.change - a.change)
    .slice(0, 5)
    .map(stock => ({
      name: stock.name,
      code: stock.code,
      currentPrice: stock.currentPrice.toFixed(2),
      change: stock.change.toFixed(2)
    }))

  // 按涨跌幅排序获取跌幅榜  
  const topLosers = allStocks
    .filter(stock => stock.change < 0)
    .sort((a, b) => a.change - b.change)
    .slice(0, 5)
    .map(stock => ({
      name: stock.name,
      code: stock.code,
      currentPrice: stock.currentPrice.toFixed(2),
      change: stock.change.toFixed(2)
    }))

  // 按成交量排序获取成交额榜
  const topVolume = allStocks
    .sort((a, b) => parseFloat(b.volume.replace(/[亿万]/g, '')) - parseFloat(a.volume.replace(/[亿万]/g, '')))
    .slice(0, 5)
    .map(stock => ({
      name: stock.name,
      code: stock.code,
      currentPrice: stock.currentPrice.toFixed(2),
      volume: stock.volume
    }))

  return {
    topGainers,
    topLosers,
    topVolume
  }
}

/**
 * 生成交易量数据
 */
function generateTradingVolume(stockCode, basePrice) {
  const today = new Date().toDateString()
  const seed = hashString(today + stockCode + 'volume')
  
  // 根据股价和随机因子生成成交量
  let volumeBase
  if (basePrice > 1000) {
    // 高价股(如茅台)成交量较小
    volumeBase = (seed % 100 + 20) / 10 // 2-12亿
  } else if (basePrice > 100) {
    // 中高价股成交量中等
    volumeBase = (seed % 200 + 50) / 10 // 5-25亿
  } else if (basePrice > 10) {
    // 中价股成交量较大
    volumeBase = (seed % 300 + 100) / 10 // 10-40亿
  } else {
    // 低价股成交量最大
    volumeBase = (seed % 500 + 200) / 10 // 20-70亿
  }
  
  if (volumeBase >= 10) {
    return volumeBase.toFixed(1) + '亿'
  } else {
    return (volumeBase * 10).toFixed(0) + '万'
  }
}
