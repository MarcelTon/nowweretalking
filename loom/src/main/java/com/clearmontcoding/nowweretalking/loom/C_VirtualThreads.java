package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.CustomException;

import java.util.stream.IntStream;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class C_VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        benchmarkStart();

        var virtualThreads = IntStream.range(0, 1_000_000)
            .mapToObj(index ->
                Thread.ofVirtual()
                    .start(() -> {
                        try {
                            Thread.sleep(2_000);
                        } catch (InterruptedException ex) {
                            throw new CustomException(ex);
                        }
                    }))
            .toList();

        for (Thread thread : virtualThreads) {
            thread.join();
        }

        benchmarkStop();
    }
}
