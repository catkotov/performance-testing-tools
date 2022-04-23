package org.cat.eye.performance.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PerformanceTestScenario<T> {

    private final List<PerformanceTestEpisodeExecutor<T>> episodes = new ArrayList<>();

    public PerformanceTestScenario<T> addEpisode(T object, Consumer<T> action, int rate, int duration) {
        this.episodes.add(new PerformanceTestEpisodeExecutor<>(object, action, rate, duration));
        return this;
    }

    public void execute() {
        if (!episodes.isEmpty()) {
            episodes.forEach(PerformanceTestEpisodeExecutor::submit);
        }
    }

}
