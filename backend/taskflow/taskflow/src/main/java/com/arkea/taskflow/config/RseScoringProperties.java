package com.arkea.taskflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "rse.scoring")
public record RseScoringProperties(
        StrategyProperties carbon,
        StrategyProperties inclusion
) {
    public record StrategyProperties(
            String name,
            int hightScore,
            int defaultScore,
            List<String> keywords
    ){}
}
