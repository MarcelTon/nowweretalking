package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class A_CompletableFuture {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        benchmarkStart();

        getMovieIds()
            .thenApplyAsync(movieIds ->
                movieIds.parallelStream()
                    .map(movieId -> {
                        var titleFuture = getTitle(movieId);
                        return titleFuture.thenCombineAsync(getReviews(movieId), Movie::new)
                            .thenAccept(System.out::println);
                    })
                    .toList())
            .handleAsync((result, exception) -> Objects.nonNull(exception) ? exception : result)
            .get();

        benchmarkStop();
    }

    private static CompletableFuture<List<Long>> getMovieIds() {
        return CompletableFuture.supplyAsync(() -> List.of(1L));
    }

    private static CompletableFuture<Title> getTitle(Long movieId) {
        System.out.println("Getting title for movie: " + movieId);
        return CompletableFuture.supplyAsync(() -> new Title(("Interstellar")));
    }

    private static CompletableFuture<List<Review>> getReviews(Long movieId) {
        System.out.println("Getting reviews for movie: " + movieId);
        return CompletableFuture.supplyAsync(() -> List.of(
            new Review("Great movie, 9/10, would watch again."),
            new Review("Woah man gravity is heavy stuff.")));
    }
}