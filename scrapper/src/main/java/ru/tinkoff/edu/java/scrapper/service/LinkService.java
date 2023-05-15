package ru.tinkoff.edu.java.scrapper.service;

import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface LinkService {

    List<Link> findOld(OffsetDateTime checkTime);

    int update(Link link);
}
