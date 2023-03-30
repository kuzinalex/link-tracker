package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.telegramapi.processor.MessageProcessor;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.webclient.dto.response.ListLinksResponse;

import java.util.List;

public class HelpCommand implements Command {

	private final String COMMAND = "/help";
	private final String DESCRIPTION = "вывести окно с командами";
	private List<Command> commands;

	public HelpCommand() {

		this.commands = List.of(new StartCommand(), new ListCommand(), new TrackCommand(), new UntrackCommand());

	}

	@Override
	public String command() {

		return COMMAND;
	}

	@Override
	public String description() {

		return DESCRIPTION;
	}

	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();

		return generateListOfCommands(chatId);
	}

	private SendMessage generateListOfCommands(Long chatId) {

		StringBuilder message = new StringBuilder( "Список поддерживаемых команд:");

		for (Command command : this.commands
		) {
			message.append(("\n"))
					.append(command.command())
					.append(" - ")
					.append(command.description());
		}
		message.append(("\n"))
				.append(command())
				.append(" - ")
				.append(description());
		return new SendMessage(chatId, message.toString());
	}
}
