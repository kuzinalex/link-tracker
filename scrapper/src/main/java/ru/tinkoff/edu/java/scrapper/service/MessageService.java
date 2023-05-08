package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.webclient.BotClient;

@Service
public class MessageService {
    private final boolean useQueue;
    private final BotClient botClient;
    private final ScrapperQueueProducer scrapperQueueProducer;

    public MessageService(ApplicationProperties config, BotClient botClient, ScrapperQueueProducer scrapperQueueProducer) {
        this.useQueue = config.useQueue();
        this.botClient = botClient;
        this.scrapperQueueProducer = scrapperQueueProducer;
    }

    public void sendMessage(LinkUpdate linkUpdate) {
        if (useQueue) {
            scrapperQueueProducer.send(linkUpdate);
        } else {
            botClient.pullLinks(linkUpdate).block();
        }
    }
}
