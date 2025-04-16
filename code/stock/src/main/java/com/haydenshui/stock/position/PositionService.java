package com.haydenshui.stock.position;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Service;
import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.position.PositionTransactionalDTO;
import com.haydenshui.stock.lib.entity.position.Position;
import com.haydenshui.stock.lib.exception.ResourceInsufficientException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.utils.RedisUtils;

import java.util.List;

@Service
public class PositionService {

    private final PositionChangeLogRepository positionChangeLogRepository;
    
    private PositionRepository repository;

    public PositionService(PositionRepository positionRepository, PositionChangeLogRepository positionChangeLogRepository) {
        this.repository = positionRepository;
        this.positionChangeLogRepository = positionChangeLogRepository;
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
    public void tradeUpdatePosition(BusinessActionContext context, PositionTransactionalDTO positionDTO){
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
    public void commitTradeUpdatePosition(BusinessActionContext context, PositionTransactionalDTO positionDTO) {
        Long positionId = (Long) context.getActionContext("positionId");
        Integer freezeAmount = (Integer) context.getActionContext("freezeAmount");
        if (positionId == null || freezeAmount == null) {
            return; 
        }
    
        String xid = context.getXid();
        String key = "TCC:" + xid + ":status";

        try {
            String status = RedisUtils.get(key);
            if ("COMMITTED".equals(status)) {
                return;
            }
            Position position = repository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("position", "[id: " + positionId + "]"));
            
            position.setQuantity(position.getQuantity() + positionDTO.getQuantity());
            if (positionDTO.getQuantity() < 0) {
                position.setFrozenQuantity(position.getFrozenQuantity() + positionDTO.getQuantity());
            }
            repository.save(position);
            RedisUtils.set(key, "COMMITTED");
    
        } catch (Exception e) {
            RedisUtils.set(key, "FAILED");
            throw e; 
        }
    }
    

    @NoTransactional
    public void rollbackTradeUpdatePosition(BusinessActionContext context, PositionTransactionalDTO positionDTO) {
        Long positionId = (Long) context.getActionContext("positionId");
        Integer freezeAmount = (Integer) context.getActionContext("freezeAmount");
        if (positionId == null || freezeAmount == null) {
            return; 
        }

        String xid = context.getXid();
        String key = "TCC:" + xid + ":status";

        try {
            String status = RedisUtils.get(key);
            if ("CANCELLED".equals(status)) {
                return;
            }

            Position position = repository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("position", "[id: " + positionId + "]"));

            position.setFrozenQuantity(position.getFrozenQuantity() - freezeAmount);
            repository.save(position);

            RedisUtils.set(key, "CANCELLED");

        } catch (Exception e) {
            RedisUtils.set(key, "FAILED");
            throw e;
        }
    }

}
