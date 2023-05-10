package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@AllArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcChatDao dao;

    @Override
    public void register(long tgChatId) throws DuplicateChatException {
        this.dao.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        this.dao.remove(tgChatId);
    }

    @Override
    public List<Long> findLinkSubscribers(Long linkId) {

        return dao.findLinkSubscribers(linkId);
    }
}
