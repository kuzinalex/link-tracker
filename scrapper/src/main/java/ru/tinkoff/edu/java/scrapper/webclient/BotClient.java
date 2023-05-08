package ru.tinkoff.edu.java.scrapper.webclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

public interface BotClient {

    static BotClient create(WebClient client) {
        return new HttpBotClient(client);
    }

    static BotClient create(WebClient.Builder builder, String url) {
        return new HttpBotClient(builder.baseUrl(url).build());
    }

    Mono<ResponseEntity> pullLinks(LinkUpdate linkUpdate);

}
