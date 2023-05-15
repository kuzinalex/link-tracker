package ru.tinkoff.edu.java.bot.exceptionhandler;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.common.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class BotExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestExceptions(Exception e) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Некорректные параметры запроса",
            HttpStatus.BAD_REQUEST.toString(),
            e.getClass().getName(),
            e.getMessage(),
            ThrowableToStringArray.convert(e)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
