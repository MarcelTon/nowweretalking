package com.clearmontcoding.nowweretalking.loom;

import java.util.stream.IntStream;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class C_ThreadsComparison {

    public static void main(String[] args) throws InterruptedException {
        benchmarkStart();

        var platformThreads = IntStream.range(0, 100_000)
            .mapToObj(index ->
                Thread.ofPlatform()
                    .unstarted(() -> {
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }))
            .toList();

        platformThreads.forEach(Thread::start);
        for (Thread thread : platformThreads) {
            thread.join();
        }

        benchmarkStop();

        benchmarkStart();

        var virtualThreads = IntStream.range(0, 1_000_000)
            .mapToObj(index ->
                Thread.ofVirtual()
                    .unstarted(() -> {
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }))
            .toList();

        virtualThreads.forEach(Thread::start);
        for (Thread thread : virtualThreads) {
            thread.join();
        }

        benchmarkStop();
    }
}
