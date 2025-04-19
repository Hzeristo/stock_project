package com.haydenshui.stock.lib.dto.position;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionChangeLogDTO {
    
    private Long id;

    private Long positionId;

    private Long securitiesAccountId;

    private String stockCode;

    private Integer changeQuantity; 

    private BigDecimal price;

    private String reason; 

    private LocalDateTime timestamp;
}
