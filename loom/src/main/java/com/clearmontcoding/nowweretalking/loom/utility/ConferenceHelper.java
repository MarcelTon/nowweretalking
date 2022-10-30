package com.clearmontcoding.nowweretalking.loom.utility;

import java.time.Duration;
import java.time.Instant;

public class ConferenceHelper {

    static Instant begin;

    private ConferenceHelper() {
    }

    public static void benchmarkStart() {
        begin = Instant.now();
    }

    public static void benchmarkStop() {
        System.out.println("Duration = " + Duration.between(begin, Instant.now()).toMillis() + " ms");
    }
}
