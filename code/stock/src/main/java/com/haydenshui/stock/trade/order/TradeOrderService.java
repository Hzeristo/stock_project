package com.haydenshui.stock.trade.order;

import java.util.List;
import java.util.Optional;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.apache.seata.rm.tcc.api.BusinessActionContextParameter;
import org.apache.seata.rm.tcc.api.LocalTCC;
import org.apache.seata.rm.tcc.api.TwoPhaseBusinessAction;
import com.haydenshui.stock.lib.dto.trade.TradeOrderDTO;
import com.haydenshui.stock.lib.entity.trade.TradeOrder;


@LocalTCC
public class TradeOrderService {
    
    private final TradeOrderRepository tradeOrderRepository;

    public TradeOrderService(TradeOrderRepository tradeOrderRepository) {
        this.tradeOrderRepository = tradeOrderRepository;
    }

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

    public Optional<TradeOrder> updateOrder(TradeOrderDTO tradeOrderDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }
    
    @TwoPhaseBusinessAction(name = "placeTradeOrder", commitMethod = "commitPlaceTradeOrder", rollbackMethod = "rollbackPlaceTradeOrder")
    public boolean placeTradeOrder(BusinessActionContext context, @BusinessActionContextParameter("OrderDTO") TradeOrderDTO tradeOrderDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeTradeOrder'");
    }

    public boolean commitPlaceTradeOrder(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commitPlaceTradeOrder'");
    }

    public boolean rollbackPlaceTradeOrder(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollbackPlaceTradeOrder'");
    }

}
