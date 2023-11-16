package com.example.MyBookShopApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBookShopAppApplication {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		SpringApplication.run(MyBookShopAppApplication.class, args);

		System.out.println("ВРЕМЯ ЗАПУСКА ПРИЛОЖЕНИЯ " +  (System.currentTimeMillis() - start)  );
	}

}
