import api from './index';

/**
 * 资金账户相关API
 */
export default {
  // 获取资金账户信息
  getCapitalAccount(id) {
    return api.get(`/capital-accounts/${id}`);
  },

  // 充值
  deposit(id, amount, data) {
    return api.post(`/capital-accounts/${id}/deposit`, { amount, ...data });
  },

  // 提现
  withdraw(id, amount, data) {
    return api.post(`/capital-accounts/${id}/withdraw`, { amount, ...data });
  },

  // 获取资金流水
  getTransactions(id, params) {
    return api.get(`/capital-accounts/${id}/transactions`, { params });
  },

  // 获取资产统计分析
  getStatistics(id, params) {
    return api.get(`/capital-accounts/${id}/statistics`, { params });
  },

  // 创建资金账户
  createCapitalAccount(data) {
    return api.post('/capital-accounts', data);
  },

  // 更新资金账户信息
  updateCapitalAccount(id, data) {
    return api.put(`/capital-accounts/${id}`, data);
  },

  // 更新资金账户状态
  updateCapitalAccountStatus(id, status) {
    return api.put(`/capital-accounts/${id}/status`, { status });
  },

  // 冻结资金
  freezeFunds(id, amount, reason) {
    return api.post(`/capital-accounts/${id}/freeze`, { amount, reason });
  }
};