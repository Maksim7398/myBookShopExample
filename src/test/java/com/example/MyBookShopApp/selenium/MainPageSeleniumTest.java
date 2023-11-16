package com.example.MyBookShopApp.selenium;

import liquibase.sdk.Main;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class MainPageSeleniumTest {

//    public static  ChromeDriver driver;



//    @BeforeAll
//    static void setup(){
//        String s = System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_win32/chromedriver.exe");
//        driver = initializeDriver(s);
//        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
//    }



//    @AfterAll
//    static void tearDown(){
//        driver.quit();
//    }

//    @Test
//    public void testMainPageAccess() throws InterruptedException {
//        MainPage mainPage = new MainPage(driver);
//        mainPage
//                .callPage()
//                .pause();
//
//        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
//
//    }
//
//}