package com.arkea.taskflow.strategy.impl;

import com.arkea.taskflow.config.RseScoringProperties;
import com.arkea.taskflow.dto.TaskResponse;
import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.strategy.RseScoringStrategy;

public class AbstractRseScoringStrategy implements RseScoringStrategy {

    private final RseScoringProperties.StrategyProperties config;

    protected AbstractRseScoringStrategy(RseScoringProperties.StrategyProperties config) {
        this.config = config;
    }

    @Override
    public int calculateRseScore(TaskResponse task) {
        if (task == null || task.title() == null) {
            return config.defaultScore();
        }

        String titleLower = task.title().toLowerCase();

        boolean hasKeyword = config.keywords().stream()
                .map(String::toLowerCase)
                .anyMatch(titleLower::contains);

        return hasKeyword ? config.hightScore() : config.defaultScore();
    }

    @Override
    public String getStrategyName() {
        return config.name();
    }
}
