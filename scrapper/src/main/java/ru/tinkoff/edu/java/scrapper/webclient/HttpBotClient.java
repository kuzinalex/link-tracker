package ru.tinkoff.edu.java.scrapper.webclient;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;

@AllArgsConstructor
public class HttpBotClient implements BotClient {

    private static final String UPDATES = "/updates";

    private WebClient client;

    @Override
    public Mono<ResponseEntity> pullLinks(LinkUpdate linkUpdate) {
        return client.post()
            .uri(uriBuilder -> uriBuilder
                .path(UPDATES)
                .build())
            .bodyValue(linkUpdate)
            .exchangeToMono(clientResponse -> {
                if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                    return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                        .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                }
                return clientResponse.bodyToMono(ResponseEntity.class);
            });
    }

}
