package com.haydenshui.stock.capital;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.dto.capital.CapitalTransactionalDTO;
import com.haydenshui.stock.lib.exception.ResourceInsufficientException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.utils.RocketMQUtils;

/**
 * 资金账户消息监听器，处理与资金相关的消息事件
 */
@Component
@RocketMQMessageListener(topic = RocketMQConstants.TOPIC_CAPITAL, consumerGroup = "capital-update-group")
public class CapitalListener implements RocketMQListener<MessageExt> {

    @Autowired
    private CapitalAccountService capitalAccountService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String tag = messageExt.getTags();
        String message = new String(messageExt.getBody());

        TransactionMessage<CapitalTransactionalDTO> msg = JSON.parseObject(message, 
            new TypeReference<TransactionMessage<CapitalTransactionalDTO>>() {});

        BusinessActionContext context = new BusinessActionContext();
        context.setActionName("tradeFreezeCapital");
        context.setXid(msg.getXid());

        CapitalTransactionalDTO dto = msg.getPayload();

        try {
            switch (tag) {
                case RocketMQConstants.TAG_TRADE_CREATE:
                    // 冻结资金
                    capitalAccountService.tradeFreezeCapital(context, dto);
                    break;
                case RocketMQConstants.TAG_TRADE_CONFIRM:
                    // 确认资金变动
                    capitalAccountService.commitTradeFreezeCapital(context, dto);
                    break;
                case RocketMQConstants.TAG_TRADE_CANCEL:
                    // 取消资金冻结
                    capitalAccountService.rollbackTradeFreezeCapital(context, dto);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported tag: " + tag);
            }
        } catch (ResourceInsufficientException e) {
            // 资金不足，发送取消交易消息
            RocketMQUtils.sendMessage("capital", RocketMQConstants.TOPIC_TRADE, RocketMQConstants.TAG_TRADE_CANCEL, context.getXid(), message);
        } catch (ResourceNotFoundException e) {
            // 资源不存在，发送取消交易消息
            RocketMQUtils.sendMessage("capital", RocketMQConstants.TOPIC_TRADE, RocketMQConstants.TAG_TRADE_CANCEL, context.getXid(), message);
        } catch (Exception ex) {
            // 其他异常，发送取消交易消息
            RocketMQUtils.sendMessage("capital", RocketMQConstants.TOPIC_TRADE, RocketMQConstants.TAG_TRADE_CANCEL, context.getXid(), message);
        }
    }
}
