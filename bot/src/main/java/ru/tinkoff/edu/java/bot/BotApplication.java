package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.telegramapi.configuration.BotProperties;
import ru.tinkoff.edu.java.bot.webclient.configuration.ScrapperProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, BotProperties.class, ScrapperProperties.class})
public class BotApplication {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(BotApplication.class, args);
		ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
		System.out.println(config);
	}
}
