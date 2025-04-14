package com.haydenshui.stock.position;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.apache.seata.rm.tcc.api.BusinessActionContextParameter;
import org.apache.seata.rm.tcc.api.LocalTCC;
import org.apache.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.stereotype.Service;
import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.position.PositionTransactionalDTO;
import com.haydenshui.stock.lib.entity.position.Position;
import com.haydenshui.stock.lib.exception.ResourceInsufficientException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;

import java.util.List;

@LocalTCC
@Service
public class PositionService {
    
    private PositionRepository repository;

    public PositionService(PositionRepository positionRepository) {
        this.repository = positionRepository;
    }

    @ServiceLog
    public Position getPositionById(Long id) {
        return repository.findById(id).orElseThrow(() -> new 
            ResourceNotFoundException("position", "[id: " + id.toString() + "]"));
    }

    @ServiceLog
    public List<Position> getPositionBySecuritiesAccountId(Long securitiesAccountId) {
        return repository.findBySecuritiesAccountId(securitiesAccountId);
    }

    @ServiceLog
    public Position getPositionBySecuritiesAccountIdAndStockId(Long securitiesAccountId, Long stockId) {
        return repository.findBySecuritiesAccountIdAndStockId(securitiesAccountId, stockId).orElseThrow(() -> new 
            ResourceNotFoundException("position", "[securitiesAccountId: " + securitiesAccountId.toString() + ", stockId: " + stockId.toString() + "]"));
    }
    
    @NoTransactional
    @TwoPhaseBusinessAction(name = "tradeUpdatePosition", commitMethod = "commitTradeUpdatePosition", rollbackMethod = "rollbackTradeUpdatePosition")
    public void tradeUpdatePosition(BusinessActionContext context, @BusinessActionContextParameter("PositionDTO") PositionTransactionalDTO positionDTO){
        Position position = repository.findById(positionDTO.getId()).orElseThrow(() -> new 
            ResourceNotFoundException("position", "[id: " + positionDTO.getId().toString() + "]"));
        int freezeAmount = 0;
        if(positionDTO.getQuantity() < 0) {
            freezeAmount = Math.abs(positionDTO.getQuantity());
            if (position.getQuantity() - position.getFrozenQuantity() < freezeAmount) {
                throw new ResourceInsufficientException("position", "[id: " + positionDTO.getId().toString() + "]" , "Buy");
            }
            position.setFrozenQuantity(position.getFrozenQuantity() + freezeAmount);
            repository.save(position);
        }
        context.getActionContext().put("freezeAmount", freezeAmount);
        context.getActionContext().put("positionId", position.getId());
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
