package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.webclient.dto.response.ListLinksResponse;

@AllArgsConstructor
@NoArgsConstructor

public class ListCommand implements Command {

	private final String COMMAND = "/list";
	private final String DESCRIPTION = "показать список отслеживаемых ссылок";
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
		ListLinksResponse response = client.getLinks(chatId).block();
		if (response.size() != 0) {
			return generateListMessage(chatId, response);
		} else {
			return new SendMessage(chatId, "Список пуст");
		}
	}

	private SendMessage generateListMessage(Long chatId, ListLinksResponse response) {

		String message = "Список отслеживаемых ссылок:";

		for (LinkResponse link : response.links()
		) {
			message.concat(link.url().toString()).concat("\n");
		}
		return new SendMessage(chatId, message);
	}
}
