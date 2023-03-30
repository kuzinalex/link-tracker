package ru.tinkoff.edu.java.bot.telegramapi.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.bot.telegramapi.command.Command;
import ru.tinkoff.edu.java.bot.telegramapi.command.HelpCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.ListCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.StartCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.TrackCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.UntrackCommand;

import java.util.List;

@AllArgsConstructor
public class TrackerBotMessageProcessor implements MessageProcessor {

	private List<? extends Command> commands;

	@Override
	public List<? extends Command> commands() {

		return commands;
	}

	@Override
	public SendMessage process(Update update) {

		for (Command command : commands()
		) {
			if (command.supports(update)){
				return command.handle(update);
			}
		}

		return new SendMessage(update.message().chat().id(), "Неизвестная команда");
	}
}
