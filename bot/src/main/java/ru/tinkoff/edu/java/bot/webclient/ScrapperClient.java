package ru.tinkoff.edu.java.bot.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.webclient.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.webclient.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.webclient.dto.response.ListLinksResponse;

public interface ScrapperClient {

	Mono<Object> registerChat(Long id);

	Mono<Object> deleteChat(Long id);

	Mono<ListLinksResponse> getLinks(Long id);

	Mono<Object> addLink(Long id, AddLinkRequest request);

	Mono<Object> deleteLink(Long id, RemoveLinkRequest request);
}