package ru.tinkoff.edu.java.scrapper.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

public interface GitHubClient {

	static GitHubClient create(WebClient client) {

		return new HttpGitHubClient(client);
	}

	static GitHubClient create(WebClient.Builder builder, String url) {

		return new HttpGitHubClient(builder.baseUrl(url).build());
	}

	Mono<GitHubResponse> fetchRepository(String user, String repository);

	Flux<GitHubEvent> fetchRepositoryEvents(String user, String repository);
}
