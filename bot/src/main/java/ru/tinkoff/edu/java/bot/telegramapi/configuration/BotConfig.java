package ru.tinkoff.edu.java.bot.telegramapi.configuration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BotConfig {

	private BotProperties properties;

	@Bean
	TelegramBot telegramBot() {

		return new TelegramBot(properties.token());
	}
}
