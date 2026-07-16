package engine;

import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.strategy.RseScoringStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RsePointsEngine {

    private final Map<String, RseScoringStrategy> strategies;

    public RsePointsEngine( List<RseScoringStrategy> strategies ) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        RseScoringStrategy::getStrategyName,
                        strategy -> strategy
                ));
    }

    public int calculateTotalScore(Task task) {
        return strategies.values().stream()
                .mapToInt(strategy -> strategy.calculateRseScore(task))
                .sum();
    }
}
