package ru.tinkoff.edu.java.scrapper.service;

public interface ChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
}
