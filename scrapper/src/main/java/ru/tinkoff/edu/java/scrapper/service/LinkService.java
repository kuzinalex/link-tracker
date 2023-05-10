package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {

	List<Link> findOld(OffsetDateTime checkTime);

	int update(Link link);
}
