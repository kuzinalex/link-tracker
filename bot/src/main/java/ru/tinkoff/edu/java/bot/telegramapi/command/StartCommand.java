package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@AllArgsConstructor
@NoArgsConstructor
public class StartCommand implements Command {

	private final String COMMAND = "/start";
	private final String DESCRIPTION = "зарегистрировать пользователя";
	private ScrapperClient client;

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
		var response=client.registerChat(chatId).block();

		return new SendMessage(chatId, "Чат зарегистрирован");
	}
}
