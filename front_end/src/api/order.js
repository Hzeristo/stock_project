import api from './index';

/**
 * 交易订单相关API
 */
export default {
  // 创建订单
  createOrder(orderData) {
    return api.post('/orders', orderData);
  },

  // 获取订单详情
  getOrder(id) {
    return api.get(`/orders/${id}`);
  },

  // 获取订单列表（支持过滤、分页和排序）
  getOrders(params) {
    return api.get('/orders', { params });
  },

  // 获取特定账户的订单
  getOrdersByAccount(securitiesAccountId, params) {
    return api.get(`/orders/account/${securitiesAccountId}`, { params });
  },

  // 获取特定股票的订单
  getOrdersByStock(stockCode, params) {
    return api.get(`/orders/stock/${stockCode}`, { params });
  },

  // 取消订单
  cancelOrder(id) {
    return api.delete(`/orders/${id}`);
  },

  // 更新订单状态
  updateOrderStatus(id, status) {
    return api.put(`/orders/${id}/status`, { status });
  },

  // 获取支持的订单类型
  getOrderTypes() {
    return api.get('/order-types');
  },

  // 获取交易执行记录
  getExecution(id) {
    return api.get(`/executions/${id}`);
  },

  // 获取订单的执行记录
  getExecutionsByOrder(orderId) {
    return api.get(`/executions/order/${orderId}`);
  },

  // 获取账户的执行记录
  getExecutionsByAccount(securitiesAccountId, params) {
    return api.get(`/executions/account/${securitiesAccountId}`, { params });
  }
};