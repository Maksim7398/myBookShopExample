package com.example.MyBookShopApp.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookstoreUserRepositoryTests {


    private final BookstoreUserRepository bookstoreUserRepository;
    @Autowired
    public BookstoreUserRepositoryTests(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Test
     public void testAddNewUser() {
        BookstoreUser user = new BookstoreUser();
        user.setPassword("0000000");
        user.setPhone("79603623577");
        user.setName("user");
        user.setEmail("user@mail.ru");

        assertNotNull(bookstoreUserRepository.save(user ));
    }
}