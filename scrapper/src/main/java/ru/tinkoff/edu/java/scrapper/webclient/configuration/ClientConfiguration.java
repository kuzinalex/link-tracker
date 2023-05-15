package ru.tinkoff.edu.java.scrapper.webclient.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.HttpBotClient;
import ru.tinkoff.edu.java.scrapper.webclient.StackOverflowClient;

@Configuration
@AllArgsConstructor
public class ClientConfiguration {

    private static final String GITHUB_CLIENT = "gitHubClient";
    private static final String STACKOVERFLOW_CLIENT = "stackOverflowClient";

    private ApplicationProperties applicationProperties;

    @Bean(STACKOVERFLOW_CLIENT)
    public StackOverflowClient stackOverflowClient() {

        return StackOverflowClient.create(stackoverflowWebClient());
    }

    @Bean(GITHUB_CLIENT)
    public GitHubClient gitHubClient() {

        return GitHubClient.create(githubWebClient());
    }

    @Bean
    public WebClient githubWebClient() {

        return WebClient.builder()
            .baseUrl(applicationProperties.githubBaseUrl())
            .build();

    }

    @Bean
    public WebClient stackoverflowWebClient() {

        return WebClient.builder()
            .baseUrl(applicationProperties.stackoverflowBaseUrl())
            .build();

    }

    @Bean
    public WebClient botWebClient() {

        return WebClient.builder()
            .baseUrl(applicationProperties.botUrl())
            .build();

    }

    @Bean
    public BotClient botClient() {
        return new HttpBotClient(botWebClient());
    }
}
