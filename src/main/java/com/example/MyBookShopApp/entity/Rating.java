package com.example.MyBookShopApp.entity;

public class Rating {

    public static void main(String[] args) {
        Book book = new Book();
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(5.0);
        ratingDto.setRating(5.0);
        book.setRating(ratingDto.setRating(4.0));
        System.out.println(book.getRating());
    }
}
