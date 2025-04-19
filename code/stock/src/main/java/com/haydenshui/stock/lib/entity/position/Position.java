package com.haydenshui.stock.lib.entity.position;

import com.haydenshui.stock.lib.entity.account.SecuritiesAccount;
import com.haydenshui.stock.lib.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a position in a securities account, tracking stock holdings.
 * <p>
 * This entity keeps track of the number of shares held in an account, 
 * along with the average purchase price. It is updated whenever a trade 
 * is executed.
 * </p>
 * 
 * <p>
 * A position is uniquely defined by the combination of a securities account 
 * and a stock. It allows users to track how many shares they hold and the 
 * average price at which the shares were acquired. Each time a trade is made 
 * involving a stock, the position is updated to reflect the new holdings and 
 * the new average price.
 * </p>
 *
 * @author Hzeristo
 * @version 1.1
 * @since 2025-03-04
 * @see SecuritiesAccount
 * @see Stock
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "position", uniqueConstraints = {
        @UniqueConstraint(name = "uq_position_account_stock", columnNames = {"securities_account_id", "stock_code"})
}, indexes = {
        @Index(name = "idx_position_securities_account_id", columnList = "securities_account_id"),
        @Index(name = "idx_position_stock_code", columnList = "stock_code")
})
public class Position {

    /**
     * The unique identifier for the position.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The securities account to which this position belongs.
     * <p>
     * This field represents a relationship to a {@link SecuritiesAccount} 
     * entity, indicating which account holds this position.
     * </p>
     */
    @Column(name = "securities_account_id", nullable = false)
    private Long securitiesAccountId;

    /**
     * The stock associated with this position.
     * <p>
     * This field represents a relationship to a {@link Stock} entity, 
     * indicating the stock that is held within this position.
     * </p>
     */
    @Column(name = "stock_code", nullable = false)
    private String stockCode;

    /**
     * The number of shares held in this position.
     * <p>
     * This field represents the quantity of stock held within the 
     * given position, indicating how many shares of the stock are 
     * owned in this securities account.
     * </p>
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * The average price at which the shares were acquired.
     * <p>
     * This field represents the average price per share at which the 
     * stock in this position was purchased, recorded as a decimal with 
     * precision for accuracy in financial calculations.
     * </p>
     */
    @Column(name = "average_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal averagePrice;

    /**
     * Flag indicating whether the position is frozen.
     * <p>
     * If this field is true, the position is frozen and cannot be traded.
     * </p>
     */
    @Column(name = "is_frozen", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isFrozen = false;

    /**
     * The number of shares currently frozen in this position.
     * <p>
     * This field represents the quantity of shares currently frozen in the position, 
     * indicating the number of shares that are in trading or pending execution.
     * </p>
     */
    @Column(name = "frozen_quantity", nullable = false)
    private Integer frozenQuantity = 0;

    /**
     * The timestamp when the position was created.
     * <p>
     * This field automatically records the timestamp when a position is created.
     * </p>
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the position was last updated.
     * <p>
     * This field automatically records the timestamp whenever a position is updated.
     * </p>
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
