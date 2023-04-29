package ru.tinkoff.edu.java.bot.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.service.UpdateService;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;

@RestController
@Tag(name = "default")
@AllArgsConstructor
public class UpdateController {

	private final UpdateService updateService;
	@Operation(summary = "Отправить обновление")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Обновление обработано", content = {@Content(schema = @Schema)}),
			@ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
	@PostMapping(value = "/updates",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updates(@Valid @RequestBody(required = true) LinkUpdate request) {
		updateService.sendNotifications(request);
		return new ResponseEntity<>(HttpStatus.OK); // заглушка
	}
}
