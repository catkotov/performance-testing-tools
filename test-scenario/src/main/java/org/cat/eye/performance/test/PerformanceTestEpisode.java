package org.cat.eye.performance.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class PerformanceTestEpisode<T> implements Runnable {

    private final CountDownLatch latch;
    private final AtomicInteger count;
    private final Consumer<T> consumer;
    private final T t;

    public PerformanceTestEpisode(CountDownLatch latch, Consumer<T> consumer, T t) {
        this.latch = latch;
        this.count = new AtomicInteger(0);
        this.consumer = consumer;
        this.t = t;
    }

    @Override
    public void run() {
        System.out.println("Thread - " + count.addAndGet(1));
        consumer.accept(t);
        latch.countDown();
    }

}
