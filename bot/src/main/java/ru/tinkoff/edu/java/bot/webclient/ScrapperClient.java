package ru.tinkoff.edu.java.bot.webclient;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.common.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;
import ru.tinkoff.edu.java.common.dto.response.ListLinksResponse;

public interface ScrapperClient {

    Mono<ResponseEntity> registerChat(Long id);

    Mono<ResponseEntity> deleteChat(Long id);

    Mono<ListLinksResponse> getLinks(Long id);

    Mono<LinkResponse> addLink(Long id, AddLinkRequest request);

    Mono<LinkResponse> deleteLink(Long id, RemoveLinkRequest request);
}
