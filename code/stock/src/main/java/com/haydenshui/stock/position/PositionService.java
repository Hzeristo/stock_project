package com.haydenshui.stock.position;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.apache.seata.rm.tcc.api.BusinessActionContextParameter;
import org.apache.seata.rm.tcc.api.LocalTCC;
import org.apache.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.dto.position.PositionDTO;
import com.haydenshui.stock.lib.entity.position.Position;

import java.util.List;
import java.util.Optional;

@LocalTCC
@Service
public class PositionService {
    
    private PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

    @Transactional
    public Optional<Position> createPosition(PositionDTO positionDTO) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPosition'");
    }

    public Optional<Position> getPositionById(Long id) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPositionById'");
    }

    public Optional<List<Position>> getPositionBySecuritiesAccountId(Long securitiesAccountId) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPositionBySecuritiesAccountId'");
    }

    public Optional<Position> getPositionBySecuritiesAccountIdAndStockId(Long securitiesAccountId, Long stockId) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPositionBySecuritiesAccountIdAndStockId'");
    }

    @Transactional
    public Optional<Position> updatePosition(PositionDTO positionDTO) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePosition'");
    }
    
    @NoTransactional
    @TwoPhaseBusinessAction(name = "tradeUpdatePosition", commitMethod = "commitTradeUpdatePosition", rollbackMethod = "rollbackTradeUpdatePosition")
    public boolean tradeUpdatePosition(BusinessActionContext context, @BusinessActionContextParameter("PositionDTO") PositionDTO positionDTO){
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tradeUpdatePosition'");
    }

    @NoTransactional
    public boolean commitTradeUpdatePosition(BusinessActionContext context) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commitTradeUpdatePosition'");
    }

    @NoTransactional
    public boolean rollbackTradeUpdatePosition(BusinessActionContext context) {
        //TODO: auto generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollbackTradeUpdatePosition'");
    }

}
