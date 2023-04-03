package ru.tinkoff.edu.java.scrapper.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ScrapperErrorConstants {
	LINK_NOT_FOUND("Ссылка не найдена", HttpStatus.NOT_FOUND),
	CHAT_NOT_EXISTS("Чат не существует", HttpStatus.NOT_FOUND),
	DUPLICATE_LINK("Ссылка уже отслеживается", HttpStatus.NOT_FOUND),
	DUPLICATE_CHAT("Чат уже зарегистрирован", HttpStatus.NOT_FOUND),
	INCORRECT_REQUEST_PARAMETERS("Некорректные параметры запроса", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;

}
