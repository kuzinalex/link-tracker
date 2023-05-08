package ru.tinkoff.edu.java.bot.telegramapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@AllArgsConstructor
@Component
public class StartCommand implements Command {

    private final static String REGISTERED = "Чат зарегистрирован";
    private ScrapperClient client;

    @Override
    public String command() {

        return "/start";
    }

    @Override
    public String description() {

        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {

        Long chatId = update.message().chat().id();
        try {
            client.registerChat(chatId).block();
            return new SendMessage(chatId, REGISTERED);
        } catch (Exception e) {
            return new SendMessage(chatId, e.getCause().getMessage());
        }
    }
}
