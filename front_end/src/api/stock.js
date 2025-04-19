import api from './index';

/**
 * 股票行情相关API
 */
export default {
  // 搜索股票
  searchStocks(query) {
    return api.get(`/stocks/search`, { params: { query } });
  },

  // 获取股票基本信息
  getStockInfo(code) {
    return api.get(`/stocks/${code}`);
  },

  // 获取实时行情
  getRealTimeQuote(code) {
    return api.get(`/stocks/${code}/realtime`);
  },

  // 获取历史行情
  getHistoryData(code, period) {
    return api.get(`/stocks/${code}/history`, { params: { period } });
  },

  // 获取技术指标
  getIndicators(code, indicatorType) {
    return api.get(`/stocks/${code}/indicators`, { params: { type: indicatorType } });
  },

  // 创建股票价格提醒
  createAlert(alertData) {
    return api.post('/alerts', alertData);
  },

  // 获取用户设置的提醒
  getAlerts() {
    return api.get('/alerts');
  },

  // 删除提醒
  deleteAlert(id) {
    return api.delete(`/alerts/${id}`);
  },
  
  // 获取首页市场概览数据
  getMarketOverview() {
    return api.get('/dashboard/market-overview');
  },
  
  // 获取股票排行数据
  getTopStocks(type = 'gain', limit = 5) {
    return api.get('/dashboard/top-stocks', { params: { type, limit } });
  }
};