package com.WishApp.WishApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WishAppApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.filename(".env")
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		;

		SpringApplication.run(WishAppApplication.class, args);
	}

}
