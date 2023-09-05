package springschool.ranking.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springschool.ranking.exception.repository.NoSuchUserIdException;
import springschool.ranking.exception.repository.UnValidatedException;
import springschool.ranking.exception.domain.ErrorResult;
import springschool.ranking.exception.repository.DBException;
import springschool.ranking.exception.repository.DuplicatedException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedException.class)
    public ErrorResult duplicateExHandler(DuplicatedException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DBException.class)
    public ErrorResult dbExHandler(DBException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("DB_ERROR", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnValidatedException.class)
    public ErrorResult unValidatedExHandler(UnValidatedException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchUserIdException.class)
    public ErrorResult noUserIdExHandler(UnValidatedException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("LOGIN_ERROR", e.getMessage());
    }
}
