package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.MalformedURLException;
import java.time.OffsetDateTime;
import java.util.List;

public interface UpdateService {
    void checkUpdates(ParserResponse<LinkParserDTO> response, Link link) throws MalformedURLException;

    List<Link> findOld(OffsetDateTime checkTime);
}
