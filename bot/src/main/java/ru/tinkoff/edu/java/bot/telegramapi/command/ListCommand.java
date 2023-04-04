package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;
import ru.tinkoff.edu.java.common.dto.response.ListLinksResponse;

@AllArgsConstructor
@Component
public class ListCommand implements Command {

	private ScrapperClient client;

	@Override
	public String command() {

		return "/list";
	}

	@Override
	public String description() {

		return "показать список отслеживаемых ссылок";
	}

	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();
		ListLinksResponse response = client.getLinks(chatId).block();
		if (response != null && response.size() != 0) {
			return generateListMessage(chatId, response);
		} else {
			return new SendMessage(chatId, "Список пуст");
		}
	}

	private SendMessage generateListMessage(Long chatId, ListLinksResponse response) {

		StringBuilder message = new StringBuilder("<b>Список отслеживаемых ссылок:</b>");
		for (LinkResponse link : response.links()) {
			message.append("\n")
					.append(link.url());
		}
		return new SendMessage(chatId, message.toString()).parseMode(ParseMode.HTML);
	}
}
