package org.cat.eye.performance.test.demo;

import org.cat.eye.performance.test.PerformanceTestScenario;

import java.util.function.Consumer;

public class PerformanceTestScenarioDemo {
    public static void main(String[] args) {

        Consumer<String> consumer = obj -> System.out.println("Send message: " + obj);
        String obj = "to EB";

        PerformanceTestScenario<String> scenario = new PerformanceTestScenario<>();
        scenario
                .addEpisode(obj, consumer, 10, 10)
                .addEpisode(obj, consumer, 50, 40)
                .addEpisode(obj, consumer, 20, 20)
                .execute();
    }

}
