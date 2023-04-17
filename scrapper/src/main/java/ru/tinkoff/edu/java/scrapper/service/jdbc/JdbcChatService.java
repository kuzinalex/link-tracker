package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@Service
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
}
