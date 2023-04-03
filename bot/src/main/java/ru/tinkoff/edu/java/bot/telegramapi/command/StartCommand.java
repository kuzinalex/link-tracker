package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@AllArgsConstructor
@NoArgsConstructor
public class StartCommand implements Command {

	private final String REGISTERED = "Чат зарегистрирован";
	private ScrapperClient client;

	@Override
	public String command() {

		return "/start";
	}

	@Override
	public String description() {

		return "зарегистрировать пользователя";
	}

	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();
		Object response = client.registerChat(chatId).block();
		if (response == null) {
			return new SendMessage(chatId, REGISTERED);
		} else {
			return new SendMessage(chatId, ((ApiErrorResponse) response).description());
		}

	}
}
