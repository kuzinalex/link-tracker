package ru.tinkoff.edu.java.scrapper.service.jooq;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

@AllArgsConstructor
public class JooqSubscriptionService implements SubscriptionService {

    public static final String LINK_NOT_FOUND = "Ссылка не найдена";
    private final JooqLinkDao linkDao;
    private final JooqSubscriptionDao subscriptionDao;

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
            throw new LinkNotFoundException(LINK_NOT_FOUND);
        }
        if (this.subscriptionDao.remove(tgChatId, link.getId()) == 0) {
            throw new LinkNotFoundException(LINK_NOT_FOUND);
        }
        return link;
    }

    @Override
    public List<Link> listAll(long tgChatId) {

        return this.linkDao.findAll(tgChatId);
    }
}
