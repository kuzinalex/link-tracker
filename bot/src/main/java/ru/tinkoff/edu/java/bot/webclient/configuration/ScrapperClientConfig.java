package ru.tinkoff.edu.java.bot.webclient.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.webclient.HttpScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@Configuration
@AllArgsConstructor
public class ScrapperClientConfig {

    ScrapperProperties properties;

    @Bean
    public ScrapperClient scrapperClient() {

        return new HttpScrapperClient(WebClient.builder().baseUrl(properties.baseUrl()).build());
    }
}
