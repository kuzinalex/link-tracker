package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService {

	private final LinkDao dao;

	public JdbcLinkService(LinkDao dao) {

		this.dao = dao;
	}

	@Override
	public Link add(long tgChatId, URI url) throws DuplicateLinkException {

		Long id;
		Link link = this.dao.find(url);
		if (link == null) {
			id = this.dao.add(url);
			link = this.dao.find(url);
		} else {
			id = link.getId();
		}
		try {
			this.dao.addSubscription(tgChatId, id);
		} catch (DuplicateKeyException e) {
			throw new DuplicateLinkException("Error"); // сообщение не важно тк, будет перехвачено RestControllerAdvice
		}
		TransactionSynchronizationManager.getCurrentTransactionName();
		return link;
	}

	@Override
	public Link remove(long tgChatId, URI url) throws LinkNotFoundException {

		Link link = this.dao.find(url);
		int deleteCount = this.dao.remove(tgChatId, link.getId());
		if (deleteCount == 0) {
			throw new LinkNotFoundException("Error"); // сообщение не важно тк, будет перехвачено RestControllerAdvice
		}
		return link;
	}

	@Override
	public List<Link> listAll(long tgChatId) {

		return this.dao.findAll(tgChatId);
	}
}
