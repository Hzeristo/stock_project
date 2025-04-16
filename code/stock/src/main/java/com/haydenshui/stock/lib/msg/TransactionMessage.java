package com.haydenshui.stock.lib.msg;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessage<T> {

    private String xid;              

    private String businessAction;   

    private LocalDateTime timestamp;        
    
    private T payload;               

}