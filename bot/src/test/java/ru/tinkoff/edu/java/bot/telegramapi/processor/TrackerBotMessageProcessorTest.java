package ru.tinkoff.edu.java.bot.telegramapi.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.telegramapi.command.Command;
import ru.tinkoff.edu.java.bot.telegramapi.command.HelpCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.ListCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.StartCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.TrackCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.UntrackCommand;
import ru.tinkoff.edu.java.bot.webclient.HttpScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

class TrackerBotMessageProcessorTest {

	private final ScrapperClient client = mock(HttpScrapperClient.class);

	private final List<Command> commands = List.of(new ListCommand(client), new StartCommand(client), new TrackCommand(client), new UntrackCommand(client));
	private final MessageProcessor processor = new TrackerBotMessageProcessor(
			List.of(new ListCommand(client), new StartCommand(client), new TrackCommand(client), new UntrackCommand(client), new HelpCommand(commands)));

	@Test
	void processUnknownCommand() {

		Update update = new Update();
		Message message = new Message();
		Chat chat = new Chat();
		ReflectionTestUtils.setField(chat, "id", 1L);
		ReflectionTestUtils.setField(message, "chat", chat);
		ReflectionTestUtils.setField(message, "text", "/unknown");
		ReflectionTestUtils.setField(update, "message", message);
		SendMessage expected = new SendMessage(1L, "Неизвестная команда");

		SendMessage result = processor.process(update);

		assertEquals(ReflectionTestUtils.getField(result, "parameters"), (ReflectionTestUtils.getField(expected, "parameters")));

	}

	@Test
	void processKnownCommand() {

		Update update = new Update();
		Message message = new Message();
		Chat chat = new Chat();
		ReflectionTestUtils.setField(chat, "id", 1L);
		ReflectionTestUtils.setField(message, "chat", chat);
		ReflectionTestUtils.setField(message, "text", "/help");
		ReflectionTestUtils.setField(update, "message", message);
		SendMessage notExpected = new SendMessage(1L, "Неизвестная команда");

		SendMessage result = processor.process(update);

		assertNotEquals(ReflectionTestUtils.getField(result, "parameters"), (ReflectionTestUtils.getField(notExpected, "parameters")));

	}
}