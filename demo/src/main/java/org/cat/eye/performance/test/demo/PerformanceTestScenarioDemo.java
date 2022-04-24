package org.cat.eye.performance.test.demo;

import org.cat.eye.performance.test.PerformanceTestScenario;
import java.util.function.Consumer;

/**
 * This a demo of performance test scenario library using.
 */
public class PerformanceTestScenarioDemo {
    public static void main(String[] args) {
        // object on that should be done an action
        String obj = "to AWS EventsBridge";
        // action that should be done on the object
        Consumer<String> consumer = object -> System.out.println("Send message: " + object);
        // create performance test scenario and play this scenario
        new PerformanceTestScenario<String>()
                .addEpisode(obj, consumer, 10, 10)
                .addEpisode(obj, consumer, 50, 40)
                .addEpisode(obj, consumer, 20, 20)
                .play();
    }

}
