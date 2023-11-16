package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.ResourseStorage;
import com.example.MyBookShopApp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequestMapping("books")
public class BooksController {


    private final ResourseStorage storage;

    private final BookRepository bookRepository;
    @Autowired
    public BooksController(ResourseStorage storage, BookRepository bookRepository) {
        this.storage = storage;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable(value = "slug",required = false) String slug, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);
        return "/books/slug";
    }




    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file,@PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImge(file,slug);

        Book bookToUpdate = bookRepository.findBookBySlug(slug);

        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);


        return ("redirect:/books/" + slug);
    }
    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash){
        Path path = storage.getBookFilePath(hash);

        MediaType mediaType = storage.getBookFileNAme(hash);

        byte[] data = storage.getBookFileByteArray(hash);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="
        +path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length).
                body(new ByteArrayResource(data));



    }
}
