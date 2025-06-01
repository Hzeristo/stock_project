package com.haydenshui.stock.securities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.haydenshui.stock.constants.ErrorCode;
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.exception.ResourceInsufficientException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.utils.RocketMQUtils;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.TOPIC_SECURITIES, consumerGroup = RocketMQConstants.CONSUMER_SECURITIES)
public class SecuritiesListener implements RocketMQListener<MessageExt> {
    
    @SuppressWarnings("unused")
    private final SecuritiesAccountService securitiesAccountService;

    private final Map<String, Consumer<TransactionMessage<CheckDTO>>> tagHandlerMap;

    public SecuritiesListener(SecuritiesAccountService securitiesAccountService) {
        this.securitiesAccountService = securitiesAccountService;
        this.tagHandlerMap = new HashMap<>();
        this.tagHandlerMap.put(RocketMQConstants.TAG_CAPITAL_VALIDITY_CONFIRM, tmsg -> {
            securitiesAccountService.confirmDisableAccount(tmsg.getPayload());
        });
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        String tag = messageExt.getTags();
        String rawMessage = new String(messageExt.getBody());

        TransactionMessage<CheckDTO> tmsg = JSON.parseObject(
            rawMessage,
            new TypeReference<>() {}
        );
    
        Consumer<TransactionMessage<CheckDTO>>handler = tagHandlerMap.get(tag);
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported tag: " + tag);
        }
        try {
            handler.accept(tmsg);
        } catch (Exception e) {
            String errorCode = e instanceof ResourceInsufficientException ? ErrorCode.RESOURCE_INSUFFICIENT.getCode()
                               : e instanceof ResourceNotFoundException ? ErrorCode.RESOURCE_NOT_FOUND.getCode()
                               : "UNKNOWN_ERROR";
    
            tmsg.setErrorCode(errorCode);
            tmsg.setErrorMessage(e.getMessage());;
    
            RocketMQUtils.sendMessage(
                "position",
                RocketMQConstants.TOPIC_POSITION,
                RocketMQConstants.TAG_TRADE_CANCEL,
                tmsg.getMsgXid(),
                JSON.toJSONString(tmsg)
            );
        }
    
    }
}
