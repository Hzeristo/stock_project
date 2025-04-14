package com.haydenshui.stock.position;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Transaction;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.dto.position.PositionTransactionalDTO;
import com.haydenshui.stock.lib.exception.ResourceInsufficientException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.utils.RocketMQUtils;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.TOPIC_POSITION, consumerGroup = "position-update-group")
public class PositionListener implements RocketMQListener<MessageExt> {

    @Autowired
    private PositionService positionService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String tag = messageExt.getTags();
        String message = new String(messageExt.getBody());

        TransactionMessage<PositionTransactionalDTO> msg = JSON.parseObject(message, 
            new TypeReference<TransactionMessage<PositionTransactionalDTO>>() {});

        // 构建 TCC 的上下文对象
        BusinessActionContext context = new BusinessActionContext();
        context.setActionName("tradeUpdatePosition");
        context.setXid(msg.getXid());

        try {
            switch (tag) {
                case RocketMQConstants.TAG_TRADE_CREATE:
                    // 处理 try 阶段
                    PositionTransactionalDTO dto = msg.getPayload();
                    positionService.tradeUpdatePosition(context, dto);
                    break;
                case RocketMQConstants.TAG_TRADE_CONFIRM:
                    // 处理 commit 阶段
                    positionService.commitTradeUpdatePosition(context);
                    break;
                case RocketMQConstants.TAG_TRADE_CANCEL:
                    // 处理 cancel 阶段
                    positionService.rollbackTradeUpdatePosition(context);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported tag: " + tag);
            }
        } catch (ResourceInsufficientException e) {
            RocketMQUtils.sendMessage("trade", RocketMQConstants.TOPIC_POSITION, RocketMQConstants.TAG_TRADE_CANCEL, context.getXid(), message);
        } catch (Exception ex) {
            RocketMQUtils.sendMessage("trade", RocketMQConstants.TOPIC_POSITION, RocketMQConstants.TAG_TRADE_CANCEL, context.getXid(), message);
        }

        
    }
}
