package com.haydenshui.stock.trade;

import com.haydenshui.stock.lib.entity.trade.TradeExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing trade executions.
 * <p>
 * This interface provides basic CRUD operations for the {@code TradeExecution} entity
 * and additional query methods to retrieve executions by trade order, securities account, and capital account.
 * </p>
 *
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 * @see TradeExecution
 */
@Repository
public interface TradeExecutionRepository extends JpaRepository<TradeExecution, Long> {

    /**
     * Retrieves a list of trade executions associated with a given trade order.
     *
     * @param tradeOrderId the ID of the trade order, must not be {@code null}.
     * @return a list of {@code TradeExecution} objects linked to the specified trade order.
     * @throws IllegalArgumentException if {@code tradeOrderId} is {@code null}.
     * @see TradeExecution
     */
    List<TradeExecution> findByTradeOrderId(Long tradeOrderId);

    /**
     * Retrieves a list of trade executions associated with a given securities account.
     *
     * @param securitiesAccountId the ID of the securities account, must not be {@code null}.
     * @return a list of {@code TradeExecution} objects linked to the specified securities account.
     * @throws IllegalArgumentException if {@code securitiesAccountId} is {@code null}.
     * @see TradeExecution
     */
    List<TradeExecution> findBySecuritiesAccountId(Long securitiesAccountId);

    /**
     * Retrieves a list of trade executions associated with a given capital account.
     *
     * @param capitalAccountId the ID of the capital account, must not be {@code null}.
     * @return a list of {@code TradeExecution} objects linked to the specified capital account.
     * @throws IllegalArgumentException if {@code capitalAccountId} is {@code null}.
     * @see TradeExecution
     */
    List<TradeExecution> findByCapitalAccountId(Long capitalAccountId);

}
