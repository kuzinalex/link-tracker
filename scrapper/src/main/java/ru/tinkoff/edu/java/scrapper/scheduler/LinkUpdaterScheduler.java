package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.UpdateService;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {


    private final UpdateService updater;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "#{${app.scheduler.interval}}")
    public void update() throws MalformedURLException {
        //updater.update();

        log.info("Updated at {}", dateFormat.format(new Date()));
    }

}
