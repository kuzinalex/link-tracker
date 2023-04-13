package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.common.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.common.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.common.dto.response.LinkResponse;
import ru.tinkoff.edu.java.common.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.common.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "default")
public class LinkController {

	private final SubscriptionService subscriptionService;

	@Operation(summary = "Получить все отслеживаемые ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {@Content(schema = @Schema(implementation = ListLinksResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
	@GetMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id) {

		List<Link> linkList = subscriptionService.listAll(id);
		return ResponseEntity.status(HttpStatus.OK).body(convertToListLinksResponse(linkList));
	}

	@Operation(summary = "Добавить отслеживание ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
	})
	@PostMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LinkResponse> addLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id, @Valid @RequestBody AddLinkRequest request)
			throws DuplicateLinkException, URISyntaxException {

		Link link = subscriptionService.add(id, request.link());
		return ResponseEntity.status(HttpStatus.OK).body(convertToLinkResponse(link));
	}

	@Operation(summary = "Убрать отслеживание ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
			@ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
	})
	@DeleteMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LinkResponse> deleteLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id, @Valid @RequestBody RemoveLinkRequest request)
			throws LinkNotFoundException, URISyntaxException {

		Link link = subscriptionService.remove(id, request.link());
		return ResponseEntity.status(HttpStatus.OK).body(convertToLinkResponse(link));
	}

	private ListLinksResponse convertToListLinksResponse(List<Link> linkList) {

		LinkResponse[] linkResponses = linkList.stream().map(link -> {
			try {
				return convertToLinkResponse(link);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}).toArray(LinkResponse[]::new);
		return new ListLinksResponse(linkResponses, linkResponses.length);
	}

	private LinkResponse convertToLinkResponse(Link link) throws URISyntaxException {

		return new LinkResponse(link.getId(), new URI(link.getUrl()));
	}
}
