package com.arkea.taskflow.strategy.impl;

import com.arkea.taskflow.config.RseScoringProperties;
import org.springframework.stereotype.Component;

@Component
public class CarbonFootPrintStrategyImpl extends AbstractRseScoringStrategy {
    public CarbonFootPrintStrategyImpl(RseScoringProperties properties) {
        super(properties.carbon());
    }

}
