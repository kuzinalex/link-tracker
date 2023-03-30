package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.webclient.dto.response.LinkResponse;

@AllArgsConstructor
@NoArgsConstructor
public class TrackCommand implements Command {

	private final String COMMAND = "/track";
	private final String DESCRIPTION = "начать отслеживание ссылки";
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
		if (isLinkPresent(update)) {
			String link = update.message().text().split(" ")[1];
			AddLinkRequest addLinkRequest = new AddLinkRequest(link);
			LinkResponse response = client.addLink(chatId, addLinkRequest).block();
			return new SendMessage(chatId, "Ссылка добавлена: " + response.url());
		} else {
			return new SendMessage(chatId, "Ссылка не указана");
		}

	}

	private boolean isLinkPresent(Update update) {

		return update.message().text().split(" ").length > 1;
	}
}
