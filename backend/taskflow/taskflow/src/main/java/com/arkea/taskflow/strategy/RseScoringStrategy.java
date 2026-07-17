package com.arkea.taskflow.strategy;

import com.arkea.taskflow.dto.TaskResponse;
import com.arkea.taskflow.model.Task;

public interface RseScoringStrategy {
    int calculateRseScore(TaskResponse task);
    String getStrategyName();
}
