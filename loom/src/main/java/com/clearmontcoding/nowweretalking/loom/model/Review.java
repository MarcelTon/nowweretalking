package com.clearmontcoding.nowweretalking.loom.model;

public record Review(String review) {

    @Override
    public String toString() {
        return review;
    }
}
