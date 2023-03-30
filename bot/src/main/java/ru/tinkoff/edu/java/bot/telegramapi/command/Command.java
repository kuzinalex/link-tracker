package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface Command {

	String command();

	String description();

	SendMessage handle(Update update);

	default boolean supports(Update update) {

		return update.message().text().startsWith(command());
	}
}