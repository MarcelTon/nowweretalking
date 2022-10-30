package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.List;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class E_StructuredConcurrency {

    public static void main(String[] args) {
        benchmarkStart();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            getMovieIds().parallelStream()
                .map(movieId -> {
                    var title   = scope.fork(() -> getTitle(movieId));
                    var reviews = scope.fork(() -> getReviews(movieId));
                    try {
                        scope.join().throwIfFailed(ex -> new IllegalStateException("Custom exception", ex));
                        return new Movie(title.resultNow(), reviews.resultNow());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .forEach(System.out::println);
        }

        benchmarkStop();
    }

    private static List<Long> getMovieIds() {
        return List.of(1L);
    }

    private static Title getTitle(Long movieId) {
        System.out.println("Getting title for movie: " + movieId);
        return new Title(("Interstellar"));
    }

    private static List<Review> getReviews(Long movieId) {
        System.out.println("Getting reviews for movie: " + movieId);
        return List.of(
            new Review("Great movie, 9/10, would watch again."),
            new Review("Woah man gravity is heavy stuff."));
    }
}
