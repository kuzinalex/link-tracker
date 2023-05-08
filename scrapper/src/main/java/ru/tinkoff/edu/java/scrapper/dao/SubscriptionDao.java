package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Subscription;

public interface SubscriptionDao {

    int add(Long tgChatId, Long linkId);

    int remove(Long tgChatId, Long linkId);

    Subscription find(Long tgChatId, Long linkId);

}
