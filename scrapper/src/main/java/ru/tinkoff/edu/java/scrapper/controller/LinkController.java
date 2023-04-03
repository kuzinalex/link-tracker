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

@RestController
@AllArgsConstructor
@Tag(name = "default")
public class LinkController {

	@Operation(summary = "Получить все отслеживаемые ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {@Content(schema = @Schema(implementation = ListLinksResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
	@GetMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(new ListLinksResponse(new LinkResponse[]{}, 0)); //заглушка
	}

	@Operation(summary = "Добавить отслеживание ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
	})
	@PostMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LinkResponse> addLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id, @Valid @RequestBody AddLinkRequest request) {

		return ResponseEntity.status(HttpStatus.OK).body(new LinkResponse(null, request.link())); //заглушка
	}

	@Operation(summary = "Убрать отслеживание ссылки")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
			@ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
	})
	@DeleteMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LinkResponse> deleteLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Long id, @Valid @RequestBody RemoveLinkRequest request) {

		return ResponseEntity.status(HttpStatus.OK).body(new LinkResponse(null, request.link())); //заглушка
	}
}
