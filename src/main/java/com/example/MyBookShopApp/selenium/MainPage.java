package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

    private final ChromeDriver driver;

    public MainPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public MainPage callPage(){
        String url = "http://127.0.0.1:8085/";
        driver.get(url);
        return this;
    }

    public void pause() throws InterruptedException {
        Thread.sleep(2000);
    }
}
