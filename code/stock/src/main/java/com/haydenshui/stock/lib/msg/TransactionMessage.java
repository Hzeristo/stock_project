package com.haydenshui.stock.lib.msg;

import java.time.LocalDateTime;

import com.haydenshui.stock.lib.entity.tcc.TccContext;
import com.haydenshui.stock.utils.SnowflakeUtils;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessage<T> {  
    
    @Builder.Default
    private String msgXid = SnowflakeUtils.nextIdStr();

    private String businessAction;   
    
    private TccContext context;
    
    private T payload;      
    
    private String errorCode;

    private String errorMessage;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();  

}