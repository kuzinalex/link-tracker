package ru.tinkoff.edu.java.bot.webclient.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ScrapperProperties(String baseUrl) {

}
