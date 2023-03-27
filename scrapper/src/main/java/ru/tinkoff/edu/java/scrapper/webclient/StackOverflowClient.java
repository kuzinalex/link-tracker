package ru.tinkoff.edu.java.scrapper.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public interface StackOverflowClient {

	static StackOverflowClient create(WebClient client) {

		return new HttpStackOverflowClient(client);
	}

	static StackOverflowClient create(WebClient.Builder builder, String url) {

		return new HttpStackOverflowClient(builder.baseUrl(url).build());
	}

	Mono<StackOverflowResponse> fetchQuestion(String id) throws JSONException, JsonProcessingException;
}
