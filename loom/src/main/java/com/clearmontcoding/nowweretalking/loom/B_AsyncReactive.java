package com.clearmontcoding.nowweretalking.loom;

import com.clearmontcoding.nowweretalking.loom.model.CustomException;
import com.clearmontcoding.nowweretalking.loom.model.Movie;
import com.clearmontcoding.nowweretalking.loom.model.Review;
import com.clearmontcoding.nowweretalking.loom.model.Title;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class B_AsyncReactive {

    public static void main(String[] args) {
        getMovieIds()
            .flatMap(id -> Mono.zip(getTitle(id), getReviews(id)))
            .map(tuple -> new Movie(tuple.getT1(), tuple.getT2()))
            .collect(Collectors.toList())
            .doOnError(CustomException::new)
            .subscribe(System.out::println);
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
