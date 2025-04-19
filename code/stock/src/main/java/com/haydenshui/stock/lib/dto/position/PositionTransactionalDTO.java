package com.haydenshui.stock.lib.dto.position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionTransactionalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key of the related Position entity. 
     */
    private Long id;

    private Long securitiesAccountId;

    private String stockCode;

    private Integer quantity;

    private BigDecimal transactionalPrice;

    private LocalDateTime transactionTime;

}
