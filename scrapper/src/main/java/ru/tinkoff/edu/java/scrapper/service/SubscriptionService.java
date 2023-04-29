package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.util.List;

public interface SubscriptionService {
    Link add(long tgChatId, URI url) throws DuplicateLinkException;

    Link remove(long tgChatId, URI url) throws LinkNotFoundException;

    List<Link> listAll(long tgChatId);
}
