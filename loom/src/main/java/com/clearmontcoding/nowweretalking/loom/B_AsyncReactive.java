package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStart;
import static com.clearmontcoding.nowweretalking.loom.utility.ConferenceHelper.benchmarkStop;

public class B_AsyncReactive {

    public static void main(String[] args) {
        benchmarkStart();

        getMovieIds()
            .flatMap(movieId -> Mono.zip(getTitle(movieId), getReviews(movieId)))
            .map(titleReviewTuple -> new Movie(titleReviewTuple.getT1(), titleReviewTuple.getT2()))
            .collect(Collectors.toList())
            .doOnError(System.out::println)
            .subscribe(System.out::println)
            .dispose();

        benchmarkStop();
    }

    private static Flux<Long> getMovieIds() {
        return Flux.fromIterable(List.of(1L));
    }

    private static Mono<Title> getTitle(Long movieId) {
        System.out.println("Getting title for movie: " + movieId);
        return Mono.just(new Title(("Interstellar")));
    }

    private static Mono<List<Review>> getReviews(Long movieId) {
        System.out.println("Getting reviews for movie: " + movieId);
        return Mono.just(List.of(
            new Review("Great movie, 9/10, would watch again."),
            new Review("Woah man gravity is heavy stuff.")));
    }
}