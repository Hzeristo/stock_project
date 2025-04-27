package com.haydenshui.stock.trade;

import java.util.List;

import org.hibernate.dialect.function.TransactSQLStrFunction;
import org.springframework.stereotype.Service;

import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.annotation.LocalTcc;
import com.haydenshui.stock.lib.annotation.Lock;
import com.haydenshui.stock.lib.dto.position.PositionTransactionalDTO;
import com.haydenshui.stock.lib.dto.trade.TradeOrderDTO;
import com.haydenshui.stock.lib.entity.tcc.TccContext;
import com.haydenshui.stock.lib.entity.trade.TradeOrder;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.utils.RocketMQUtils;
import com.haydenshui.stock.utils.SnowflakeUtils;


@Service
public class TradeService {

    private final TradeOrderRepository tradeOrderRepository;

    private final TradeExecutionRepository traedExecutionRepository;

    public TradeService(TradeOrderRepository tradeOrderRepository, TradeExecutionRepository traedExecutionRepository) {
        this.tradeOrderRepository = tradeOrderRepository;
        this.traedExecutionRepository = traedExecutionRepository;
    }

    public void newTrade() {

    }

    public void stopTrade() {

    }

    public TradeOrder getTradeOrderById(Long id) {
        return tradeOrderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TradeOrder",  "[id: " + id + "]"));
    }

    public List<TradeOrder> getTradeOrdersByStockCode(String stockCode) {
        return tradeOrderRepository.findByStockCode(stockCode);
    }

    public List<TradeOrder> getTradeOrdersBySecuritiesId(Long securitiesAccountId) {
        return tradeOrderRepository.findBySecuritiesAccountId(securitiesAccountId);
    }

    @Lock(lockKey = "LOCK:TCC:TRADE:{tradeOrderDTO.id}")
    @LocalTcc
    public void tradeLog(TccContext context, TradeOrderDTO tradeDTO) {

    }

    @Lock(lockKey = "LOCK:TCC:TRADE:{tradeOrderDTO.id}")
    @LocalTcc
    public boolean confirmTradeLog() {
        //TODO: add logic
        return true;
    }

    @Lock(lockKey = "LOCK:TCC:TRADE:{tradeOrderDTO.id}")
    @LocalTcc
    public void rollbackTradeLog() {

    }

}
