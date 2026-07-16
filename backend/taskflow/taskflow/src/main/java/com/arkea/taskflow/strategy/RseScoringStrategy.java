package com.arkea.taskflow.strategy;

import com.arkea.taskflow.model.Task;

public interface RseScoringStrategy {
    int calculateRseScore(Task task);
    String getStrategyName();
}
