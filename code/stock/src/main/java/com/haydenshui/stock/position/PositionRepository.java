package com.haydenshui.stock.position;

import com.haydenshui.stock.lib.entity.position.Position;
import com.haydenshui.stock.lib.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Position} entities.
 *
 * <p>Provides methods to query positions based on securities accounts and stocks.</p>
 *
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    /**
     * Finds a position by securities account and stock.
     *
     * @param securitiesAccount The securities account associated with the position.
     * @param stock The stock associated with the position.
     * @return An optional containing the position if found, otherwise empty.
     * @see Position
     */
    Optional<Position> findBySecuritiesAccountIdAndStockCode(Long securitiesAccountId, String stockCode);
    
    /**
     * Finds all positions associated with a given securities account.
     *
     * @param securitiesAccount The securities account.
     * @return A list of positions associated with the given account.
     * @see Position
     */
    List<Position> findBySecuritiesAccountId(Long securitiesAccountId);
    
    /**
     * Finds all positions associated with a given stock.
     *
     * @param stock The stock.
     * @return A list of positions associated with the given stock.
     * @see Position
     */
    List<Position> findByStockCode(String stockCode);
}
