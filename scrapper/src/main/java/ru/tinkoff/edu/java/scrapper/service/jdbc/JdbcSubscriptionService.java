package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class JdbcSubscriptionService implements SubscriptionService {

	private final JdbcLinkDao linkDao;
	private final JdbcSubscriptionDao subscriptionDao;

	@Override
	@Transactional
	public Link add(long tgChatId, URI url) throws DuplicateLinkException {

		Long id;
		Link link = this.linkDao.find(url);
		if (link == null) {
			id = this.linkDao.add(url);
			link = this.linkDao.find(url);
		} else {
			id = link.getId();
		}
		try {
			this.subscriptionDao.add(tgChatId, id);
		} catch (DuplicateKeyException e) {
			throw new DuplicateLinkException("Ссылка уже отслеживается");
		}
		return link;
	}

	@Override
	@Transactional
	public Link remove(long tgChatId, URI url) throws LinkNotFoundException {

		Link link = this.linkDao.find(url);
		if (link == null) {
			throw new LinkNotFoundException("Ссылка не найдена");
		}
		if (this.subscriptionDao.remove(tgChatId, link.getId()) == 0) {
			throw new LinkNotFoundException("Ссылка не найдена");
		}
		return link;
	}

	@Override
	@Transactional
	public List<Link> listAll(long tgChatId) {

		return this.linkDao.findAll(tgChatId);
	}
}
