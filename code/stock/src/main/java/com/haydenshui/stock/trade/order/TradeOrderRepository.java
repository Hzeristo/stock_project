package com.haydenshui.stock.trade.order;

import com.haydenshui.stock.lib.entity.trade.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing trade orders.
 * <p>
 * This interface provides basic CRUD operations for the {@code TradeOrder} entity
 * and additional query methods to retrieve trade orders by securities account ID or stock code.
 * </p>
 *
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 */
@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

    /**
     * Retrieves a list of trade orders associated with a given securities account ID.
     * <p>
     * This method retrieves a list of trade orders associated with the specified securities account ID.
     * </p>
     *
     * @param securitiesAccountId the ID of the securities account
     * @return a list of {@code TradeOrder} objects linked to the specified securities account;
     * may be empty if no orders match the securities account ID
     * 
     * @throws IllegalArgumentException if the securities account ID is null
     * @see TradeOrder
     */
    List<TradeOrder> findBySecuritiesAccountId(Long securitiesAccountId);

    /**
     * Retrieves a list of trade orders associated with a given stock code.
     *
     * @param stockCode the stock code of the traded asset
     * @return a list of {@code TradeOrder} objects related to the specified stock code;
     * may be empty if no orders match the stock code
     * 
     * @throws IllegalArgumentException if the stock code is null or empty
     * @see TradeOrder
     */
    List<TradeOrder> findByStockCode(String stockCode);

    /**
     * Finds a trade order by its unique ID.
     *
     * @param id the ID of the trade order
     * @return an {@code Optional} containing the trade order if found, otherwise {@code Optional.empty()}
     * 
     * @throws IllegalArgumentException if the ID is null
     * @see TradeOrder
     */
    Optional<TradeOrder> findById(Long id);
}
