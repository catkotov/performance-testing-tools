package org.cat.eye.performance.test;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * This class provides action on an object that should be repeatedly done during the performance testing.
 * As an example you can repeatedly send requests to a service.
 *
 * @param <T> type of the object on that action should be done.
 */
public class PerformanceTestAction<T> implements Runnable {

    private final CountDownLatch latch;
    private final Consumer<T> action;
    private final T actionObject;

    /**
     * Constructor of the action executor.
     *
     * @param latch - count down latch for figuring out actions number during performance testing.
     * @param action - action on an object.
     * @param actionObject - object that will be used by the action.
     */
    public PerformanceTestAction(CountDownLatch latch, Consumer<T> action, T actionObject) {
        this.latch = latch;
        this.action = action;
        this.actionObject = actionObject;
    }

    @Override
    public void run() {
        action.accept(actionObject);
        latch.countDown();
    }

}
