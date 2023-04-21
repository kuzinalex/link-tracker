package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.update.UpdateService;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {

    private final UpdateService updater;
    private final LinkParser linkParser;
    private final ApplicationProperties properties;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "#{${app.scheduler.interval}}")
    public void update() throws MalformedURLException {

        List<Link> links = updater.findOld(OffsetDateTime.now().minusMinutes(properties.oldLinkInterval()));
        ParserResponse<LinkParserDTO> response;
        for (Link link : links) {
            response = linkParser.parse(new URL(link.getUrl()));
            updater.checkUpdates(response, link);
        }
        log.info("Checked {} links at {}", links.size(), dateFormat.format(new Date()));
    }

}
