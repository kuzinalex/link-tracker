package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.webclient.dto.response.LinkResponse;

@AllArgsConstructor
@NoArgsConstructor
public class UntrackCommand implements Command {

	public static final String REPLY = "Укажите ссылку для прекращения отслеживания";
	private final String LINK_DELETED = "Ссылка удалена: ";
	private final String PLACE_HOLDER = "Ссылка на GitHub или StackOverflow";
	private ScrapperClient client;

	@Override
	public String command() {

		return "/untrack";
	}

	@Override
	public String description() {

		return "прекратить отслеживание ссылки";
	}

	@Override
	public SendMessage handle(Update update) {

		Long chatId = update.message().chat().id();
		if (isReply(update)) {
			String link = update.message().text();
			RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(link);
			Object response = client.deleteLink(chatId, removeLinkRequest).block();
			try {
				return new SendMessage(chatId, LINK_DELETED + ((LinkResponse) response).url());
			} catch (ClassCastException e) {
				return new SendMessage(chatId, ((ApiErrorResponse) response).description());
			}
		} else {
			return new SendMessage(chatId, REPLY).replyMarkup(new ForceReply().inputFieldPlaceholder(PLACE_HOLDER));
		}
	}

	private boolean isReply(Update update) {

		return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(REPLY);
	}
}
