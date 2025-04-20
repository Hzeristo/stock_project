package com.haydenshui.stock.lib.msg;

import java.time.LocalDateTime;

import com.haydenshui.stock.lib.entity.tcc.TccContext;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessage<T> {   

    private String businessAction;   
    
    private TccContext context;
    
    private T payload;      
    
    private String errorCode;

    private String errorMessage;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();  

}