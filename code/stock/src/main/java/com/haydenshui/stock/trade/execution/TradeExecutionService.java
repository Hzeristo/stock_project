package com.haydenshui.stock.trade.execution;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.lib.dto.trade.TradeExecutionDTO;
import com.haydenshui.stock.lib.entity.trade.TradeExecution;

public class TradeExecutionService {
    
    private final TradeExecutionRepository tradeExecutionRepostory;

    public TradeExecutionService(TradeExecutionRepository tradeExecutionRepostory) {
        this.tradeExecutionRepostory = tradeExecutionRepostory;
    }

    @Transactional
    public Optional<TradeExecution> createExecution(TradeExecutionDTO tradeExecutionDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createExecution'");
    }

    public Optional<TradeExecution> getExecutionById(Long id) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExecutionById'");
    }

    public Optional<List<TradeExecution>> getExecutionByAccount(Long securitiesAccountId) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExecutionByAccount'");
    }

    @Transactional
    public Optional<TradeExecution> updateExecution(TradeExecutionDTO tradeExecutionDTO) {
        //TODO: Auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateExecution'");
    }

}
