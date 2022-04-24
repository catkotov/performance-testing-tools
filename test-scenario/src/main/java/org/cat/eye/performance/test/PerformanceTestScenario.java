package org.cat.eye.performance.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class combines list of the performance test episodes into on performance test scenario.
 * You can combine three performance test episodes into one scenario, fist episode - do actions with rate 10 actions/second
 * during 10 seconds, second episode - do actions with rate 50 actions/second during 40 seconds and third episode - do
 * actions with rate 20 actions/second during 30 seconds, and then play this scenario as single performance test.
 *
 * @param <T> type of the object on that actions scenario should be done.
 */
public class PerformanceTestScenario<T> {

    private final List<PerformanceTestEpisode<T>> episodes = new ArrayList<>();

    /**
     * This method adds performance test episode to the scenario.
     *
     * @param object - object on that episode should be done.
     * @param action - action on the object for scenario.
     * @param rate - actions rate in action/second.
     * @param duration - duration of the episode.
     * @return performance test scenario object.
     */
    public PerformanceTestScenario<T> addEpisode(T object, Consumer<T> action, int rate, int duration) {
        this.episodes.add(new PerformanceTestEpisode<>(object, action, rate, duration));
        return this;
    }

    /**
     * This method starts to play performance test scenario.
     */
    public void play() {
        if (!episodes.isEmpty()) {
            episodes.forEach(PerformanceTestEpisode::submit);
        }
    }

}
