package com.clearmontcoding.nowweretalking.loom.model;

public record Title(String title) {

    @Override
    public String toString() {
        return title;
    }
}
