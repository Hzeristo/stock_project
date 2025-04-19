import api from './index';

/**
 * 账户相关API
 */
export default {
  // 获取账户信息
  getAccount(accountId) {
    return api.get(`/accounts/${accountId}`);
  },

  // 更新账户信息
  updateAccount(accountId, accountData) {
    return api.put(`/accounts/${accountId}`, accountData);
  },

  // 获取账户状态
  getAccountStatus(accountId) {
    return api.get(`/accounts/${accountId}/status`);
  },

  // 更新安全设置
  updateSecuritySettings(accountId, securityData) {
    return api.put(`/accounts/${accountId}/security`, securityData);
  },

  // 获取风险评估
  getRiskAssessment(accountId) {
    return api.get(`/accounts/${accountId}/risk-assessment`);
  },

  // 创建个人账户
  createIndividualAccount(accountData) {
    return api.post('/accounts/individual', accountData);
  },

  // 创建企业账户
  createCorporateAccount(accountData) {
    return api.post('/accounts/corporate', accountData);
  },

  // 用户登录
  login(credentials) {
    return api.post('/auth/login', credentials);
  },

  // 用户登出
  logout() {
    return api.post('/auth/logout');
  }
};