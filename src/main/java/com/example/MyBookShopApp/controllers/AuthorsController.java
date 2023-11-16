package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.entity.Author;
import com.example.MyBookShopApp.service.AuthorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorsService authorsService;
    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap(){
        Map<String,List<Author>>authorsMap = authorsService.getAuthorsMap();

        return authorsService.getAuthorsMap();
    }


    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors(){

        return authorsService.getAuthorsMap();
    }

}
