package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramapi.bot.Bot;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

@Service
@AllArgsConstructor
public class UpdateService {

    private final Bot bot;

    public void sendNotifications(LinkUpdate update) {

        for (Long tgChatId : update.tgChatIds()) {
            bot.execute(new SendMessage(tgChatId, "Обновления доступны в : " + update.url() + "\n" + update.description()));
        }
    }
}
