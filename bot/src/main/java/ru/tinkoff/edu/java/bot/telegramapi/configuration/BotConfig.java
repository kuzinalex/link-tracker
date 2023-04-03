package ru.tinkoff.edu.java.bot.telegramapi.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegramapi.bot.TrackerBot;
import ru.tinkoff.edu.java.bot.telegramapi.command.Command;
import ru.tinkoff.edu.java.bot.telegramapi.command.HelpCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.ListCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.StartCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.TrackCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.UntrackCommand;
import ru.tinkoff.edu.java.bot.telegramapi.processor.MessageProcessor;
import ru.tinkoff.edu.java.bot.telegramapi.processor.TrackerBotMessageProcessor;
import ru.tinkoff.edu.java.bot.webclient.configuration.ScrapperClientConfig;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BotConfig {

	private BotProperties properties;
	private ScrapperClientConfig scrapperClientConfig;

	@Bean
	TrackerBot trackerBot() {

		MessageProcessor messageProcessor = messageProcessor();
		BotCommand[] botCommands = messageProcessor.commands().stream().map(Command::toApiCommand).toArray(BotCommand[]::new);
		TelegramBot bot = new TelegramBot(properties.token());
		bot.execute(new SetMyCommands(botCommands));
		return new TrackerBot(bot, messageProcessor);
	}

	@Bean
	MessageProcessor messageProcessor() {

		List<? extends Command> commands = List.of(
				new HelpCommand(),
				new StartCommand(scrapperClientConfig.scrapperClient()),
				new ListCommand(scrapperClientConfig.scrapperClient()),
				new TrackCommand(scrapperClientConfig.scrapperClient()),
				new UntrackCommand(scrapperClientConfig.scrapperClient()));
		return new TrackerBotMessageProcessor(commands);
	}

}
