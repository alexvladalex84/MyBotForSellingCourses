package ru.telegramBot.telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotNewProgectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotNewProgectApplication.class, args);
	}

}
