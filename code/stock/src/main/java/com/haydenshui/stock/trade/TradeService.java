package com.haydenshui.stock.trade;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.seata.spring.annotation.GlobalTransactional;
import com.haydenshui.stock.lib.dto.trade.TradeDTO;
import com.haydenshui.stock.trade.execution.TradeExecutionService;
import com.haydenshui.stock.trade.order.TradeOrderService;



public class TradeService {
    
    private final RocketMQTemplate template;

    private final TradeOrderService tradeOrderService;

    private final TradeExecutionService tradeExecutionService;

    public TradeService(RocketMQTemplate template, TradeOrderService tradeOrderService, TradeExecutionService tradeExecutionService) {
        this.template = template;
        this.tradeOrderService = tradeOrderService;
        this.tradeExecutionService = tradeExecutionService;
    }

    @GlobalTransactional
    public void newTrade(TradeDTO tradeDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newTrade'");
    }

    public void stopTrade(TradeDTO tradeDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stopTrade'");
    }

}
