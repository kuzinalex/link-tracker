package ru.tinkoff.edu.java.scrapper.dao.jpa;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.jpa.ChatEntity;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class JpaChatDao implements ChatDao {

	private final JpaChatRepository chatRepository;

	@Override
	public int add(Long tgChatId) throws DuplicateChatException {

		if (chatRepository.findById(tgChatId).isPresent()) {
			throw new DuplicateChatException("Чат уже зарегистрирован");
		} else {
			ChatEntity chat = new ChatEntity();
			chat.setId(tgChatId);
			chat.setSubscription(new HashSet<>());
			chatRepository.save(chat);
			return 1;
		}
	}

	@Override
	public int remove(Long tgChatId) {

		chatRepository.deleteById(tgChatId);
		return 1;
	}

	@Override
	public List<Long> findLinkSubscribers(Long linkId) {

		return chatRepository.findAllByLink(linkId);
	}

	@Override
	public List<Chat> findAll() {

		return chatRepository.findAll().stream().map(this::entityToChat).toList();
	}

	private Chat entityToChat(ChatEntity chatEntity) {

		Chat chat = new Chat();
		chat.setId(chatEntity.getId());
		return chat;
	}
}
