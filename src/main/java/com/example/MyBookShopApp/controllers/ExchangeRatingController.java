package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.entity.Book;
import com.example.MyBookShopApp.entity.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("books")
public class ExchangeRatingController {

    private final BookRepository bookRepository;
    @Autowired
    public ExchangeRatingController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @PostMapping("/rateBook/{slug}" )
    public String exchangeRating(@PathVariable("slug") String slug,Model model){
        RatingDto ratingDto = new RatingDto();
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("rating",ratingDto);
        book.setRating(ratingDto.getRating());
        Logger.getLogger(ExchangeRatingController.class.getSimpleName()).info(book.getRating().toString());
        return "books/slug";
    }

}
