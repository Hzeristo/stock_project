package com.haydenshui.stock.lib.dto.position;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    
    /**
     * The unique identifier for the position.
     * This field represents the ID of the position.
     */
    private Long id;

    /**
     * The ID of the associated securities account.
     * This field represents the ID of the account where this position is held.
     */
    private Long securitiesAccountId; 

    /**
     * The ID of the associated stock.
     * This field represents the ID of the stock within this position.
     */
    private String stockCode;

    /**
     * The number of shares held in this position.
     * This field represents how many shares of the stock are held in the account.
     */
    private Integer quantity;

    /**
     * The average price at which the shares were acquired.
     * This field represents the average purchase price per share for the stock in this position.
     */
    private BigDecimal averagePrice;

    /**
     * Indicates whether the position is currently frozen.
     * This field represents whether the position is currently frozen or not.
     */
    private boolean isFrozen = false;

    /**
     * The number of shares currently frozen in this position.
     */
    private Integer frozenQuantity = 0; 

    /**
     * The timestamp when the position was created.
     * This field stores the date and time when this position was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp when the position was last updated.
     * This field stores the date and time when this position was last updated.
     */
    private LocalDateTime updatedAt;

}
