package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Chat;

import java.util.List;

public interface ChatDao {
    int add(Long tgChatId);

    int remove(Long tgChatId);

    List<Long> findByLink(Long linkId);
    List<Chat> findAll();
}
