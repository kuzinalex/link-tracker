package ru.tinkoff.edu.java.bot.telegramapi.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

import ru.tinkoff.edu.java.bot.telegramapi.command.Command;

public interface MessageProcessor {

	List<? extends Command> commands();

	SendMessage process(Update update);
}