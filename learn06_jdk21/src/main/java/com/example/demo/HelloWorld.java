package com.example.demo;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class HelloWorld {

    public static void main(String[] args) {

        System.out.println("Hello World");

        // 虚拟线程
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println("虚拟线程" + i);
                    return i;
                });
            });
        }

        // 平台线程
        try (var executor = Executors.newFixedThreadPool(100)) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println("平台线程" + i);
                    return i;
                });
            });
        }
    }
}
