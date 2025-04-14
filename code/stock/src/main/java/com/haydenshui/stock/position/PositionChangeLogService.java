package com.haydenshui.stock.position;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haydenshui.stock.lib.dto.position.PositionChangeLogDTO;
import com.haydenshui.stock.lib.dto.position.PositionMapper;
import com.haydenshui.stock.lib.entity.position.PositionChangeLog;

@Service
public class PositionChangeLogService {

    private final PositionChangeLogRepository repository;

    public PositionChangeLogService(PositionChangeLogRepository repository) {
        this.repository = repository;
    }

    public PositionChangeLog recordChange(PositionChangeLogDTO dto) {
        PositionChangeLog entity = PositionMapper.toEntity(dto);
        return repository.save(entity);
    }

    public List<PositionChangeLog> getLogsByPositionId(Long positionId) {
        return repository.findByPositionId(positionId);
    }

    public List<PositionChangeLog> getLogsBySecuritiesAccountId(Long accountId) {
        return repository.findBySecuritiesAccountId(accountId);
    }

    public List<PositionChangeLog> getLogsByStockCode(String stockCode) {
        return repository.findByStockCode(stockCode);
    }

    public List<PositionChangeLog> getLogsBySecuritiesAccountIdAndStockCode(Long accountId, String stockCode) {
        return repository.findBySecuritiesAccountIdAndStockCode(accountId, stockCode);
    }
}
