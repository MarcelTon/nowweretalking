package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.CustomException;
import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class D_ExecutorService {

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            getMovieIds()
                .parallelStream()
                .map(movieId -> {
                    var titleFuture = executor.submit(() -> getTitle(movieId));
                    var reviewFuture = executor.submit(() -> getReviews(movieId));
                    try {
                        return new Movie(titleFuture.get(), reviewFuture.get());
                    } catch (InterruptedException | ExecutionException ex) {
                        throw new CustomException(ex);
                    }
                })
                .forEach(System.out::println);
        }
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
