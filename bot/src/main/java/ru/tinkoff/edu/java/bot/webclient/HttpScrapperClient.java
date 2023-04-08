package ru.tinkoff.edu.java.bot.webclient;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.common.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;
import ru.tinkoff.edu.java.common.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.common.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;

@AllArgsConstructor
public class HttpScrapperClient implements ScrapperClient {

	private static final String TG_CHAT = "/tg-chat";
	private static final String SEPARATOR = "/";

	public static final String LINKS = "/links";

	public static final String TG_CHAT_ID = "Tg-Chat-Id";

	private WebClient client;

	public HttpScrapperClient(WebClient.Builder builder, String baseUrl) {

		this.client = builder.baseUrl(baseUrl).build();
	}

	@Override
	public Mono<ResponseEntity> registerChat(Long id) {

		return client.post()
				.uri(uriBuilder -> uriBuilder
						.path(TG_CHAT + SEPARATOR + id)
						.build())
				.exchangeToMono(clientResponse -> {
					if (clientResponse.statusCode().is4xxClientError()) {
						return clientResponse.bodyToMono(ApiErrorResponse.class)
								.flatMap(apiErrorResponse -> Mono.error(new DuplicateChatException(apiErrorResponse.description())));
					}
					return clientResponse.bodyToMono(ResponseEntity.class);
				});
	}

	@Override
	public Mono<ResponseEntity> deleteChat(Long id) {

		return client.delete()
				.uri(uriBuilder -> uriBuilder
						.path(TG_CHAT + SEPARATOR + id)
						.build())
				.exchangeToMono(clientResponse -> {
					if (clientResponse.statusCode().is4xxClientError()) {
						return clientResponse.bodyToMono(ApiErrorResponse.class)
								.flatMap(apiErrorResponse -> Mono.error(new ChatNotFoundException(apiErrorResponse.description())));
					}
					return clientResponse.bodyToMono(ResponseEntity.class);
				});
	}

	@Override
	public Mono<ListLinksResponse> getLinks(Long id) {

		return client.get()
				.uri(uriBuilder -> uriBuilder
						.path(LINKS)
						.build())
				.header(TG_CHAT_ID, String.valueOf(id))
				.retrieve()
				.bodyToMono(ListLinksResponse.class);
	}

	@Override
	public Mono<LinkResponse> addLink(Long id, AddLinkRequest request) {

		return client.post()
				.uri(uriBuilder -> uriBuilder
						.path(LINKS)
						.build())
				.header(TG_CHAT_ID, String.valueOf(id))
				.bodyValue(request)
				.exchangeToMono(clientResponse -> {
					if (clientResponse.statusCode().is4xxClientError()) {
						return clientResponse.bodyToMono(ApiErrorResponse.class)
								.flatMap(apiErrorResponse -> Mono.error(new DuplicateLinkException(apiErrorResponse.description())));
					}
					return clientResponse.bodyToMono(LinkResponse.class);
				});
	}

	@Override
	public Mono<LinkResponse> deleteLink(Long id, RemoveLinkRequest request) {

		return client.method(HttpMethod.DELETE)
				.uri(uriBuilder -> uriBuilder
						.path(LINKS)
						.build())
				.header(TG_CHAT_ID, String.valueOf(id))
				.bodyValue(request)
				.exchangeToMono(clientResponse -> {
					if (clientResponse.statusCode().is4xxClientError()) {
						return clientResponse.bodyToMono(ApiErrorResponse.class)
								.flatMap(apiErrorResponse -> Mono.error(new LinkNotFoundException(apiErrorResponse.description())));
					}
					return clientResponse.bodyToMono(LinkResponse.class);
				});
	}
}
