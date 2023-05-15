package ru.tinkoff.edu.java.scrapper.service.jdbc;

import java.util.List;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

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
