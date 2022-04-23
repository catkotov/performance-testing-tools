package org.cat.eye.performance.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This class provides action on an object that should be repeatedly done during the performance testing.
 * As an example you can repeatedly send requests to a service.
 *
 * @param <T> type of the object
 */
public class PerformanceTestEpisode<T> implements Runnable {

    private final CountDownLatch latch;
    private final AtomicInteger count;
    private final Consumer<T> action;
    private final T actionObject;

    /**
     * Constructor of the action executor.
     *
     * @param latch - count down latch for figuring out actions number during performance testing.
     * @param action - action on an object.
     * @param actionObject - object that will be used by the action.
     */
    public PerformanceTestEpisode(CountDownLatch latch, Consumer<T> action, T actionObject) {
        this.latch = latch;
        this.count = new AtomicInteger(0);
        this.action = action;
        this.actionObject = actionObject;
    }

    @Override
    public void run() {
        System.out.println("Thread - " + count.addAndGet(1));
        action.accept(actionObject);
        latch.countDown();
    }

}
