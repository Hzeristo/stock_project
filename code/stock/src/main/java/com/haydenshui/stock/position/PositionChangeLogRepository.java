package com.haydenshui.stock.position;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haydenshui.stock.lib.entity.position.PositionChangeLog;

@Repository
public interface PositionChangeLogRepository extends JpaRepository<PositionChangeLog, Long> {

    List<PositionChangeLog> findByPositionId(Long positionId);

    List<PositionChangeLog> findByStockCode(String stockCode);

    List<PositionChangeLog> findBySecuritiesAccountId(Long accountId);

    List<PositionChangeLog> findBySecuritiesAccountIdAndStockCode(Long accountId, String stockCode);

}