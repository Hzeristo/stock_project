package com.haydenshui.stock.securities.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.SecuritiesAccount;

@Component
public class SecuritiesAccountStrategyFactory {

    private final List<SecuritiesAccountStrategy<?, ?>> strategies;

    public SecuritiesAccountStrategyFactory(List<SecuritiesAccountStrategy<?, ?>> strategies) {
        this.strategies = strategies;
    }

    @SuppressWarnings("unchecked")//没事的，策略类型都是正确的
    public <T extends SecuritiesAccount, D extends SecuritiesAccountDTO> SecuritiesAccountStrategy<T, D> getStrategy(String type) {
        for (SecuritiesAccountStrategy<?, ?> strategy : strategies) {
            if (strategy.matches(type)) {
                return (SecuritiesAccountStrategy<T, D>) strategy; 
            }
        }
        throw new IllegalArgumentException("No strategy found for type " + type);
    }

}
