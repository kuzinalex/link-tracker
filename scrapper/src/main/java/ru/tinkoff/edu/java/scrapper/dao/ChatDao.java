package ru.tinkoff.edu.java.scrapper.dao;

import java.util.List;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

public interface ChatDao {

    int add(Long tgChatId) throws DuplicateChatException;

    int remove(Long tgChatId);

    List<Long> findLinkSubscribers(Long linkId);

    List<Chat> findAll();
}
