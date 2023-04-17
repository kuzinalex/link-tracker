package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

//@Service
@AllArgsConstructor
public class JooqChatService implements ChatService {

	private final JooqChatDao chatDao;

	@Override
	public void register(long tgChatId) throws DuplicateChatException {
		chatDao.add(tgChatId);
	}

	@Override
	public void unregister(long tgChatId) {
		chatDao.remove(tgChatId);
	}
}
