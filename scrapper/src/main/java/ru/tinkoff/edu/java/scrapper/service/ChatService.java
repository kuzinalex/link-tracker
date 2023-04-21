package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.common.exception.DuplicateChatException;

import java.util.List;

public interface ChatService {
    void register(long tgChatId) throws DuplicateChatException;
    void unregister(long tgChatId);

    List<Long> findLinkSubscribers(Long linkId);
}
