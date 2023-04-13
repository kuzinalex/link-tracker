package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkDao {

	long add(URI url);

	int update(Link link);

	int remove(Long link);

	Link find(URI url);

	List<Link> findAll(Long tgChatId);

	List<Link> findOld(OffsetDateTime checkTime);
}
