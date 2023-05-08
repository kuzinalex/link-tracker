package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationProperties(@NotNull String test, @NotNull Scheduler scheduler, String githubBaseUrl,
                                    String stackoverflowBaseUrl, String botUrl, Long oldLinkInterval, @NotNull AccessType databaseAccessType,
                                    @NotNull String queueName, @NotNull String exchangeName, @NotNull boolean useQueue) {

    @Override
    public String githubBaseUrl() {

        if (githubBaseUrl == null || githubBaseUrl.isEmpty()) {
            return "https://api.github.com";
        } else {
            return githubBaseUrl;
        }
    }

    @Override
    public String stackoverflowBaseUrl() {

        if (stackoverflowBaseUrl == null || stackoverflowBaseUrl.isEmpty()) {
            return "https://api.stackexchange.com/2.3";
        } else {
            return stackoverflowBaseUrl;
        }
    }

    @Override
    public String botUrl() {

        if (botUrl == null || botUrl.isEmpty()) {
            return "http://localhost:8080";
        } else {
            return botUrl;
        }

    }

    @Override
    public Long oldLinkInterval() {

        if (oldLinkInterval == null) {
            return 30L;
        } else {
            return oldLinkInterval;
        }
    }
}
