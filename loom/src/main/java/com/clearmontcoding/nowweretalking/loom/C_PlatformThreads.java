package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.CustomException;

import java.util.stream.IntStream;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class C_PlatformThreads {

    public static void main(String[] args) throws InterruptedException {
        benchmarkStart();

        var platformThreads = IntStream.range(0, 100_000)
            .mapToObj(index ->
                Thread.ofPlatform()
                    .start(() -> {
                        try {
                            Thread.sleep(2_000);
                        } catch (InterruptedException ex) {
                            throw new CustomException(ex);
                        }
                    }))
            .toList();

        for (Thread thread : platformThreads) {
            thread.join();
        }

        benchmarkStop();
    }
}
