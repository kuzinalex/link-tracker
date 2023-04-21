package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@AllArgsConstructor
public class JpaChatService implements ChatService {

	private final JpaChatDao jpaChatDao;

	@Override
	public void register(long tgChatId) throws DuplicateChatException {

		jpaChatDao.add(tgChatId);
	}

	@Override
	public void unregister(long tgChatId) {

		jpaChatDao.remove(tgChatId);
	}

	@Override
	public List<Long> findLinkSubscribers(Long linkId) {

		return jpaChatDao.findLinkSubscribers(linkId);
	}
}
