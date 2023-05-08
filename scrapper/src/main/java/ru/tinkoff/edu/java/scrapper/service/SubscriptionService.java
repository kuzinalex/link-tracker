package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.util.List;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface SubscriptionService {
    Link add(long tgChatId, URI url) throws DuplicateLinkException;

    Link remove(long tgChatId, URI url) throws LinkNotFoundException;

    List<Link> listAll(long tgChatId);
}
