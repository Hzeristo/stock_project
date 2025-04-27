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
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.dto.capital.CapitalCheckDTO;
import com.haydenshui.stock.lib.msg.TransactionMessage;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.TOPIC_SECURITIES, consumerGroup = RocketMQConstants.CONSUMER_SECURITIES)
public class SecuritiesListener implements RocketMQListener<MessageExt> {
    
    private final SecuritiesAccountService securitiesAccountService;

    private final Map<String, Consumer<TransactionMessage<CapitalCheckDTO>>> tagHandlerMap;

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

        TransactionMessage<CapitalCheckDTO> tmsg = JSON.parseObject(
            rawMessage,
            new TypeReference<>() {}
        );
    
        Consumer<TransactionMessage<CapitalCheckDTO>>handler = tagHandlerMap.get(tag);
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported tag: " + tag);
        }
        try {
            handler.accept(tmsg);
        } catch (Exception e) {
            //TODO: add exceptions
        }
    
    }
}
