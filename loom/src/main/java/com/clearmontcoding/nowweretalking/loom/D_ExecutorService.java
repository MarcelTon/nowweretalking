package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class D_ExecutorService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        benchmarkStart();

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(D_ExecutorService::getMovieIds).get()
                .parallelStream()
                .map(movieId -> {
                    var titleFuture = executorService.submit(() -> getTitle(movieId));
                    var reviewFuture = executorService.submit(() -> getReviews(movieId));
                    try {
                        return new Movie(titleFuture.get(), reviewFuture.get());
                    } catch (InterruptedException | ExecutionException ex) {
                        throw new IllegalStateException("Double exception handling sadness.");
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
