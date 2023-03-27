package ru.tinkoff.edu.java.scrapper.webclient;

import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

@AllArgsConstructor
public class HttpStackOverflowClient implements StackOverflowClient {

	private static final String QUESTIONS = "/questions";
	private static final String SITE_PARAM = "site";
	private static final String SITE_STACKOVERFLOW_VALUE = "stackoverflow";
	private static final String SEPARATOR = "/";

	private WebClient client;

	@Override
	public Mono<StackOverflowResponse> fetchQuestion(String id) {

		return client
				.get()
				.uri(uriBuilder -> uriBuilder
						.path(QUESTIONS + SEPARATOR + id)
						.queryParam(SITE_PARAM, SITE_STACKOVERFLOW_VALUE)
						.build())
				.retrieve()
				.bodyToMono(StackOverflowResponse.class)
				.onErrorResume(throwable -> {
					System.out.println(throwable);
					return Mono.empty();
				});
	}
}
