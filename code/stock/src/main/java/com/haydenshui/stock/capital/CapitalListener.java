package com.haydenshui.stock.capital;

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
import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.msg.TransactionMessage;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.TOPIC_CAPITAL, consumerGroup = "capital-update-group")
public class CapitalListener implements RocketMQListener<MessageExt> {

    private final Map<String, Consumer<String>> tagHandlerMap = new HashMap<>();

    public CapitalListener(CapitalAccountService capitalService) {
        tagHandlerMap.put(RocketMQConstants.TAG_CAPITAL_VALIDITY_CHECK, raw -> {
            TransactionMessage<CheckDTO> tmsg = JSON.parseObject(
                raw,
                new TypeReference<TransactionMessage<CheckDTO>>() {}
            );
            capitalService.disableValidity(tmsg.getPayload(), "fund"); 
        });
        tagHandlerMap.put(RocketMQConstants.TAG_CAPITAL_VALIDITY_CONFIRM, raw -> {
        });

        tagHandlerMap.put(RocketMQConstants.TAG_TRADE_CREATE, raw -> {
            TransactionMessage<CapitalAccountTransactionDTO> tmsg = JSON.parseObject(
                raw,
                new TypeReference<TransactionMessage<CapitalAccountTransactionDTO>>() {}
            );
            capitalService.freezeAmount(tmsg.getContext(), tmsg.getPayload(), CapitalAccountType.TRADE);
        });
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        String tag = messageExt.getTags();
        String raw = new String(messageExt.getBody());

        Consumer<String> handler = tagHandlerMap.get(tag);
        if (handler != null) {
            handler.accept(raw);
        } 
    }
}

