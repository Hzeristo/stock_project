package com.haydenshui.stock.lib.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckDTO {
    
    private Long securitiesAccountId; //check trade order only

    private Long capitalAccountId;
    
    private String type;

    private boolean passed;

    private String reason;

}
