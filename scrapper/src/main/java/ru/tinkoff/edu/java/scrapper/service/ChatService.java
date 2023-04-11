package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.common.exception.DuplicateChatException;

public interface ChatService {
    void register(long tgChatId) throws DuplicateChatException;
    void unregister(long tgChatId);
}
