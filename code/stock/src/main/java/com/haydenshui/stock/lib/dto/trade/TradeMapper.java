package com.haydenshui.stock.lib.dto.trade;

import org.springframework.beans.BeanUtils;

import com.haydenshui.stock.lib.entity.trade.OrderKind;
import com.haydenshui.stock.lib.entity.trade.OrderType;
import com.haydenshui.stock.lib.entity.trade.OrderValidity;
import com.haydenshui.stock.lib.entity.trade.TradeExecution;
import com.haydenshui.stock.lib.entity.trade.TradeOrder;

public class TradeMapper {
    
    private TradeMapper() {}

    public static TradeOrderDTO toDTO(TradeOrder tradeOrder) {
        if(tradeOrder == null)
            return null;
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        BeanUtils.copyProperties(tradeOrder, tradeOrderDTO);
        tradeOrderDTO.setOrderType(tradeOrder.getOrderType() != null ? tradeOrder.getOrderType().toString() : null);
        tradeOrderDTO.setOrderValidity(tradeOrder.getOrderValidity() != null ? tradeOrder.getOrderValidity().toString() : null);
        tradeOrderDTO.setOrderKind(tradeOrder.getOrderKind() != null ? tradeOrder.getOrderKind().toString() : null);
        return tradeOrderDTO;
    }

    public static TradeExecutionDTO toDTO(TradeExecution tradeExecution) {
        if(tradeExecution == null)
            return null;
        TradeExecutionDTO tradeExecutionDTO = new TradeExecutionDTO();
        BeanUtils.copyProperties(tradeExecution, tradeExecutionDTO);
        return tradeExecutionDTO;
    }

    public static TradeOrder toEntity(TradeOrderDTO tradeOrderDTO) {
        if(tradeOrderDTO == null)
            return null;
        TradeOrder tradeOrder = new TradeOrder();
        BeanUtils.copyProperties(tradeOrderDTO, tradeOrder);
        tradeOrder.setOrderType(OrderType.fromString(tradeOrderDTO.getOrderType()));
        tradeOrder.setOrderValidity(OrderValidity.fromString(tradeOrderDTO.getOrderValidity()));
        tradeOrder.setOrderKind(OrderKind.fromString(tradeOrderDTO.getOrderKind()));
        return tradeOrder;
    }

    public static TradeExecution toEntity(TradeExecutionDTO tradeExecutionDTO) {
        if(tradeExecutionDTO == null)
            return null;
        TradeExecution tradeExecution = new TradeExecution();
        BeanUtils.copyProperties(tradeExecutionDTO, tradeExecution);
        return tradeExecution;
    }

}
