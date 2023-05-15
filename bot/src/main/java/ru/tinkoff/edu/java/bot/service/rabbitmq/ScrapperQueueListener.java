package ru.tinkoff.edu.java.bot.service.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.UpdateService;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

@Service
@RabbitListener(queues = "#{${app.queueName}}")
@AllArgsConstructor
public class ScrapperQueueListener {

    private final UpdateService updateService;

    @RabbitHandler
    public void listen(LinkUpdate update) {
        updateService.sendNotifications(update);
    }
}
