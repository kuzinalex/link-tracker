package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@Service
public class JdbcChatService implements ChatService {

    private final ChatDao dao;

    public JdbcChatService(ChatDao dao) {
        this.dao = dao;
    }

    @Override
    public void register(long tgChatId) throws DuplicateChatException {
        this.dao.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        this.dao.remove(tgChatId);
    }
}
