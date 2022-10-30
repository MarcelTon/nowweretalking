package com.clearmontcoding.nowweretalking.loom.model;

import java.util.List;

public record Movie(Title title, List<Review> reviews) {

    @Override
    public String toString() {
        return "Movie{" +
            "title=" + title +
            ", reviews=" + reviews +
            '}';
    }
}
