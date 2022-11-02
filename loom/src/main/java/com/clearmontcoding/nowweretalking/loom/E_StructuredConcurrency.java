package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.CustomException;
import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.List;

public class E_StructuredConcurrency {

    public static void main(String[] args) {
        getMovieIds().parallelStream()
            .forEach(movieId -> {
                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                    var title = scope.fork(() -> getTitle(movieId));
                    var reviews = scope.fork(() -> getReviews(movieId));

                    scope.join().throwIfFailed(CustomException::new);

                    System.out.println(new Movie(title.resultNow(), reviews.resultNow()));
                } catch (InterruptedException ex) {
                    throw new CustomException(ex);
                }
            });
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
