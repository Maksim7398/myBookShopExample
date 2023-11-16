package com.example.MyBookShopApp.entity;

import liquibase.pro.packaged.S;

import java.util.*;

public class RatingDto {
    Double rating;
    List<Double> listR = new ArrayList<>();
    public List<Double> getListR() {
        return listR;
    }
    public RatingDto() {
    }
    public Double setRating(Double stars_count) {
        listR.add(stars_count);
        rating = listR.stream().mapToDouble(r -> r.doubleValue()).sum();
        this.rating = rating / listR.size();
        return rating;
    }
    public Double getRating() {
        return  rating;
    }
}
