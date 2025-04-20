import api from './index';

/**
 * 持仓管理相关API
 */
export default {
  // 获取用户所有持仓
  getPositions(params) {
    return api.get('/positions', { params });
  },

  // 获取单个持仓详情
  getPosition(id) {
    return api.get(`/positions/${id}`);
  },

  // 获取特定账户的所有持仓
  getPositionsByAccount(securitiesAccountId) {
    return api.get(`/positions/account/${securitiesAccountId}`);
  },

  // 获取特定账户的特定股票持仓
  getPositionByAccountAndStock(securitiesAccountId, stockId) {
    return api.get(`/positions/account/${securitiesAccountId}/stock/${stockId}`);
  },

  // 获取持仓分析
  getPositionAnalysis(params) {
    return api.get('/positions/analysis', { params });
  },

  // 设置止损
  setStopLoss(id, data) {
    return api.post(`/positions/${id}/stop-loss`, data);
  },

  // 设置止盈
  setTakeProfit(id, data) {
    return api.post(`/positions/${id}/take-profit`, data);
  },

  // 获取历史持仓变动
  getPositionHistory(params) {
    return api.get('/positions/history', { params });
  },
  
  // 获取持仓变动日志
  getPositionChangeLog(positionId, params) {
    return api.get(`/positions/${positionId}/logs`, { params });
  }
};