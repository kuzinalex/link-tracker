package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RestController
@Tag(name = "default")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован", content = {@Content(schema = @Schema)}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
    @PostMapping(value = "/tg-chat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerChat(@PathVariable(required = true) Long id) throws DuplicateChatException {
        chatService.register(id);
        return new ResponseEntity<>(HttpStatus.OK); // заглушка
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён", content = {@Content(schema = @Schema)}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Чат не существует", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping(value = "/tg-chat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteChat(@PathVariable(required = true) Long id) {
        chatService.unregister(id);
        return new ResponseEntity<>(HttpStatus.OK); // заглушка
    }

}
