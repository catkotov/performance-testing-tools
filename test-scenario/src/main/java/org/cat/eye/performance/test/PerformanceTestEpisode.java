package org.cat.eye.performance.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * This class provides an execution of a periodic action with defined rate and duration.
 *
 * @param <T> type of the object on that actions episode should be done.
 */
public class PerformanceTestEpisode<T> {

    private final int rate;
    private final CountDownLatch latch;
    private final ScheduledThreadPoolExecutor service;
    private final PerformanceTestAction<T> testEpisode;

    /**
     * Constructor of the actions' executor.
     *
     * @param object - object on that action should be done.
     * @param action - periodically executed action.
     * @param rate - actions execution rate (in actions/second).
     * @param duration - durations of the actions (in seconds).
     */
    public PerformanceTestEpisode(T object, Consumer<T> action, int rate, int duration) {

        this.rate = rate;
        latch = new CountDownLatch(rate * duration);

        if (rate <= 500)
            service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(rate);
        else
            service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(500);

        service.setRemoveOnCancelPolicy(true);

        testEpisode = new PerformanceTestAction<>(latch, action, object);
    }

    /**
     * This method submits a series ot the actions.
     */
    public void submit() {

        service.scheduleAtFixedRate(
                this.testEpisode,
                0,
                1000 / rate,
                TimeUnit.MILLISECONDS
        );

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        service.shutdownNow();
    }

}
