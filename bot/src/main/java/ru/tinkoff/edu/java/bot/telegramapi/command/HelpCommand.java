package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class HelpCommand implements Command {

	private List<Command> commands;

	@PostConstruct
	private void postConstruct() {

		this.commands.add(this);
	}

	@Override
	public String command() {

		return "/help";
	}

	@Override
	public String description() {

		return "вывести окно с командами";
	}

	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();
		return generateHelpsMessage(chatId);
	}

	private SendMessage generateHelpsMessage(Long chatId) {

		StringBuilder message = new StringBuilder("<b>Список поддерживаемых команд:</b>");
		for (Command command : this.commands) {
			message.append(("\n"))
					.append("<b>")
					.append(command.command())
					.append("</b>")
					.append(" - ")
					.append(command.description());
		}
		return new SendMessage(chatId, message.toString()).parseMode(ParseMode.HTML);
	}
}
