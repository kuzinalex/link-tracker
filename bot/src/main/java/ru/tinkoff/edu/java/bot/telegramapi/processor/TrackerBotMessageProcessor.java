package ru.tinkoff.edu.java.bot.telegramapi.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegramapi.command.Command;
import ru.tinkoff.edu.java.bot.telegramapi.command.TrackCommand;
import ru.tinkoff.edu.java.bot.telegramapi.command.UntrackCommand;

@Component
public class TrackerBotMessageProcessor implements MessageProcessor {

    public TrackerBotMessageProcessor(List<? extends Command> commands) {

        this.commands = commands;
    }

    private List<? extends Command> commands;

    @Override
    public List<? extends Command> commands() {

        return commands;
    }

    @Override
    public SendMessage process(Update update) {

        for (Command command : commands()) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        if (isTrackState(update)) {
            return commands().stream().filter(command -> command instanceof TrackCommand).findFirst().orElseThrow().handle(update);
        }
        if (isUntrackState(update)) {
            return commands().stream().filter(command -> command instanceof UntrackCommand).findFirst().orElseThrow().handle(update);
        }

        return new SendMessage(update.message().chat().id(), "Неизвестная команда");
    }

    private boolean isTrackState(Update update) {

        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(TrackCommand.REPLY);
    }

    private boolean isUntrackState(Update update) {

        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(UntrackCommand.REPLY);
    }
}
