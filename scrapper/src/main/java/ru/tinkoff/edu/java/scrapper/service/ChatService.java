package ru.tinkoff.edu.java.scrapper.service;

import java.util.List;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;

public interface ChatService {
    void register(long tgChatId) throws DuplicateChatException;

    void unregister(long tgChatId);

    List<Long> findLinkSubscribers(Long linkId);
}
