package org.cat.eye.performance.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PerformanceTestEpisodeExecutor<T> {

    private final int rate;

    private final CountDownLatch latch;
    private final ScheduledThreadPoolExecutor service;

    private final PerformanceTestEpisode<T> testEpisode;

    public PerformanceTestEpisodeExecutor(T t, Consumer<T> action, int rate, int duration) {

        this.rate = rate;
        latch = new CountDownLatch(rate * duration);

        if (rate <= 500)
            service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(rate);
        else
            service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(500);

        service.setRemoveOnCancelPolicy(true);

        testEpisode = new PerformanceTestEpisode<>(latch, action, t);
    }

    public void submit() {
        System.out.println("Start");

        service.scheduleAtFixedRate(
                this.testEpisode,
                0,
                1000 / rate,
                TimeUnit.MILLISECONDS
        );

        try {
            latch.await();
        } catch (InterruptedException e) {

        }

        service.shutdownNow();

        System.out.println("Отстанов");
    }

}
