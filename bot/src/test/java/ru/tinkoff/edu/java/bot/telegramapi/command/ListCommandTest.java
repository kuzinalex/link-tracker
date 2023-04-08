package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;
import ru.tinkoff.edu.java.common.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {

	@InjectMocks
	private ListCommand command;

	@Mock
	private ScrapperClient client;

	@Test
	public void testLinksListMessage() throws URISyntaxException {

		Update update = new Update();
		Message message = new Message();
		Chat chat = new Chat();
		LinkResponse[] links = {
				new LinkResponse(1L, new URI("https://github.com/kuzinalex/tinkoff-tracker")),
				new LinkResponse(2L, new URI("https://github.com/kuzinalex/tinkoff-tracker2"))
		};
		ListLinksResponse expectedLinksResponse = new ListLinksResponse(links, 2);
		SendMessage expected = generateListMessage(1L, expectedLinksResponse);
		ReflectionTestUtils.setField(chat, "id", 1L);
		ReflectionTestUtils.setField(message, "chat", chat);
		ReflectionTestUtils.setField(update, "message", message);

		when(client.getLinks(anyLong())).thenReturn(Mono.just(expectedLinksResponse));

		SendMessage sendMessage = command.handle(update);

		assertEquals(ReflectionTestUtils.getField(sendMessage, "parameters"), (ReflectionTestUtils.getField(expected, "parameters")));
	}

	@Test
	public void testEmptyLinksListMessage() {

		Update update = new Update();
		Message message = new Message();
		Chat chat = new Chat();
		ListLinksResponse expectedLinksResponse = new ListLinksResponse(new LinkResponse[0], 0);
		SendMessage expected = new SendMessage(1L, "Список пуст");
		ReflectionTestUtils.setField(chat, "id", 1L);
		ReflectionTestUtils.setField(message, "chat", chat);
		ReflectionTestUtils.setField(update, "message", message);

		when(client.getLinks(anyLong())).thenReturn(Mono.just(expectedLinksResponse));

		SendMessage sendMessage = command.handle(update);

		assertEquals(ReflectionTestUtils.getField(sendMessage, "parameters"), (ReflectionTestUtils.getField(expected, "parameters")));
	}

	private SendMessage generateListMessage(Long chatId, ListLinksResponse response) {

		StringBuilder message = new StringBuilder("<b>Список отслеживаемых ссылок:</b>");
		for (LinkResponse link : response.links()
		) {
			message.append("\n")
					.append(link.url());
		}
		return new SendMessage(chatId, message.toString()).parseMode(ParseMode.HTML);
	}
}