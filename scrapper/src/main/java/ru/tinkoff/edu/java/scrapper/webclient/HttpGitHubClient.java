package ru.tinkoff.edu.java.scrapper.webclient;

import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

@AllArgsConstructor
public class HttpGitHubClient implements GitHubClient {

    private static final String REPOS = "/repos";
    private static final String EVENTS = "/events";
    private static final String PER_PAGE_PARAM = "per_page";
    private static final String SEPARATOR = "/";
    private WebClient client;

    @Override
    public Mono<GitHubResponse> fetchRepository(String user, String repository) {

        return client
            .get()
            .uri(REPOS + SEPARATOR + user + SEPARATOR + repository)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .onErrorResume(throwable -> Mono.empty());
    }

    @Override
    public Flux<GitHubEvent> fetchRepositoryEvents(String user, String repository) {

        return client
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(REPOS + SEPARATOR + user + SEPARATOR + repository + EVENTS)
                .queryParam(PER_PAGE_PARAM, 20)
                .build())
            .retrieve()
            .bodyToFlux(GitHubEvent.class);

    }
}
