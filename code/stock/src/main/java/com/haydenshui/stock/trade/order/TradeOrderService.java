package com.haydenshui.stock.trade.order;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.dto.trade.TradeOrderDTO;
import com.haydenshui.stock.lib.entity.trade.TradeOrder;

@Service
public class TradeOrderService {
    
    private final TradeOrderRepository tradeOrderRepository;

    public TradeOrderService(TradeOrderRepository tradeOrderRepository) {
        this.tradeOrderRepository = tradeOrderRepository;
    }

    @Transactional
    public Optional<TradeOrder> createOrder(TradeOrderDTO tradeOrderDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }


    public Optional<TradeOrder> getOrderById(Long id) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

    public Optional<List<TradeOrder>> getOrderByAccount(Long securitiesAccountId) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderByAccount'");
    }

    @Transactional
    public Optional<TradeOrder> updateOrder(TradeOrderDTO tradeOrderDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }
    
    @NoTransactional
    public boolean placeTradeOrder(TradeOrderDTO tradeOrderDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeTradeOrder'");
    }

    @NoTransactional
    public boolean commitPlaceTradeOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commitPlaceTradeOrder'");
    }

    @NoTransactional
    public boolean rollbackPlaceTradeOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollbackPlaceTradeOrder'");
    }

}
