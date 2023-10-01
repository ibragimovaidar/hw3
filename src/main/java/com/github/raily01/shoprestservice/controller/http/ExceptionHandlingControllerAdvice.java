package com.github.raily01.shoprestservice.controller.http;

import com.github.raily01.shoprestservice.exception.ShopRestServiceException;
import com.github.raily01.shoprestservice.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("Необрабатываемая ошибка", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Необрабатываемая ошибка сервера"));
    }

    @ExceptionHandler(ShopRestServiceException.class)
    public ResponseEntity<ErrorResponse> handleShopRestServiceException(ShopRestServiceException e) {
        log.info("Неудачный ответ", e);
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getErrorDescription()));
    }
}
