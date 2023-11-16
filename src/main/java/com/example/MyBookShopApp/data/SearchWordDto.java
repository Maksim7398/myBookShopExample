package com.example.MyBookShopApp.data;

import lombok.Getter;

@Getter
public class SearchWordDto {

    private String example;

    public SearchWordDto(String example) {
        this.example = example;
    }

    public SearchWordDto() {
    }

    public void setExample(String example) {
        this.example = example;
    }
}
