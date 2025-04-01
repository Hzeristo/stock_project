package com.haydenshui.stock.capital.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.haydenshui.stock.lib.entity.account.CapitalAccountType;

@Component
public class CapitalAccountStrategyFactory {
    
    private final List<CapitalAccountStrategy> strategies;

    public CapitalAccountStrategyFactory(List<CapitalAccountStrategy> strategies) {
        this.strategies = strategies;
    }

    public CapitalAccountStrategy getStrategy(CapitalAccountType type) {
        return strategies.stream()
                .filter(strategy -> strategy.matches(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for type: " + type));
    }

}
