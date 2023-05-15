package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

@AllArgsConstructor
public class JpaSubscriptionService implements SubscriptionService {

    public static final String LINK_NOT_FOUND = "Ссылка не найдена";
    private final JpaLinkDao linkDao;
    private final JpaSubscriptionDao subscriptionDao;

    @Override
    public Link add(long tgChatId, URI url) throws DuplicateLinkException {

        Long id;
        Link link = this.linkDao.find(url);
        if (link == null) {
            id = this.linkDao.add(url);
            link = this.linkDao.find(url);
        } else {
            id = link.getId();
        }
        if (this.subscriptionDao.add(tgChatId, id) == 0) {
            throw new DuplicateLinkException("Ссылка уже отслеживается");
        }
        return link;
    }

    @Override
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
