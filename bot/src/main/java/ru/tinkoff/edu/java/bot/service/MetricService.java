package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Service;

@Service
public class MetricService {
    public void incrementHandledMessageCount() {
        Metrics.counter("handled_message_count").increment();
    }
}
