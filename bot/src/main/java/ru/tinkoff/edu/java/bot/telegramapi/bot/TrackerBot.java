package ru.tinkoff.edu.java.bot.telegramapi.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.MetricService;
import ru.tinkoff.edu.java.bot.telegramapi.command.Command;
import ru.tinkoff.edu.java.bot.telegramapi.processor.MessageProcessor;

@Component
@AllArgsConstructor
public class TrackerBot implements Bot {

    private TelegramBot bot;
    private MessageProcessor processor;
    private MetricService metricService;

    @PostConstruct
    private void postConstruct() {

        this.bot.setUpdatesListener(this);
        BotCommand[] botCommands = processor.commands().stream().map(Command::toApiCommand).toArray(BotCommand[]::new);
        bot.execute(new SetMyCommands(botCommands));
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {

        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {
            SendMessage message = processor.process(update);
            execute(message);
            metricService.incrementHandledMessageCount();
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {

        this.bot.shutdown();
    }
}
