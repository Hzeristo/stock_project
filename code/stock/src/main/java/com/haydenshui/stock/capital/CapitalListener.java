package com.haydenshui.stock.capital;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

@Component
@RocketMQMessageListener(topic = "TRADE_CAPITAL_UPDATE", consumerGroup = "capital-update-group")
public class CapitalListener implements RocketMQListener<String> {

    @Autowired
    private CapitalAccountService capitalService;

    @Override
    public void onMessage(String message) {
        
    }

    
}
