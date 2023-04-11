package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.common.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;

import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@Component
public class TrackCommand implements Command {

	public static final String REPLY = "Укажите ссылку для отслеживания";
	private final String LINK_ADDED = "Ссылка добавлена: ";
	private final String PLACE_HOLDER = "Ссылка на GitHub или StackOverflow";
	private final String NOT_VALID_LINK = "Неверный формат ссылки";
	private ScrapperClient client;

	@Override
	public String command() {

		return "/track";
	}

	@Override
	public String description() {

		return "начать отслеживание ссылки";
	}

	@SneakyThrows
	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();
		if (isReply(update)) {
			URL link = null;
			try {
				link = new URL(update.message().text());
			} catch (MalformedURLException e) {
				return new SendMessage(chatId, NOT_VALID_LINK);
			}
			AddLinkRequest addLinkRequest = new AddLinkRequest(link.toURI());
			try {
				LinkResponse response = client.addLink(chatId, addLinkRequest).block();
				return new SendMessage(chatId, LINK_ADDED + response.url());
			} catch (Exception e) {
				return new SendMessage(chatId, e.getCause().getMessage());
			}
		} else {
			return new SendMessage(chatId, REPLY).replyMarkup(new ForceReply().inputFieldPlaceholder(PLACE_HOLDER));
		}
	}

	private boolean isReply(Update update) {

		return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(REPLY);
	}
}
