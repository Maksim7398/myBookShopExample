package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {

    private final BookRepository bookRepository;
    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBookByAuthor_FirstName() {
        String token = "Collin";
        List<Book> bookListByAuthorFirstName = bookRepository.findBookByAuthor_FirstName(token);

        assertNotNull(bookListByAuthorFirstName);
        assertFalse(bookListByAuthorFirstName.isEmpty());

        for (Book book : bookListByAuthorFirstName){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getAuthor().getFirstName().contains(token));
        }
    }

    @Test
    void findBooksByTitleContaining() {
        String token = "Life";
        List<Book> bookListByTitleContaining = bookRepository.findBooksByTitleContaining(token);
        assertNotNull(bookListByTitleContaining);
        assertFalse(bookListByTitleContaining.isEmpty());

        for (Book book : bookListByTitleContaining){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle().contains(token));
        }


    }

    @Test
    void getBestsellers() {
        List<Book> bookListIsBestellers = bookRepository.getBestsellers();
        assertNotNull(bookListIsBestellers);
        assertFalse(bookListIsBestellers.isEmpty());
        assertThat(bookListIsBestellers.size()).isGreaterThan(1);

//        for (Book book : bookListIsBestellers){
//            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
//            assertThat(book.getIsBestseller()\);
//
//        }



    }
}