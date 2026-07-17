package com.arkea.taskflow.strategy.impl;

import com.arkea.taskflow.config.RseScoringProperties;
import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.strategy.RseScoringStrategy;
import org.springframework.stereotype.Component;

@Component
public class DigitalInclusionStrategyImpl extends AbstractRseScoringStrategy {
    public DigitalInclusionStrategyImpl(RseScoringProperties properties) {
       super(properties.inclusion());
    }

}
