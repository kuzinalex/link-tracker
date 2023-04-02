package ru.tinkoff.edu.java.bot.telegramapi.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import ru.tinkoff.edu.java.bot.telegramapi.processor.MessageProcessor;

import java.util.List;

public class TrackerBot implements Bot {

	private TelegramBot bot;
	private MessageProcessor processor;

	public TrackerBot(TelegramBot bot, MessageProcessor processor) {

		this.processor = processor;
		this.bot = bot;
		this.bot.setUpdatesListener(this);
	}

	@Override
	public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {

		bot.execute(request);
	}

	@Override
	public int process(List<Update> updates) {

		for (Update update : updates
		) {
			SendMessage message = processor.process(update);
			execute(message);
		}

		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	@Override
	public void close() {

		this.bot.shutdown();
	}
}
