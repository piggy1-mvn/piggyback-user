package com.incentives.piggyback.user.util.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.incentives.piggyback.user.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UserServiceExceptionAdvice {
        @ResponseBody
        @ExceptionHandler(UserNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String userNotFoundHandler(UserNotFoundException ex) {

                return ex.getMessage();
        }

        @ResponseBody
        @ExceptionHandler(IOException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ResponseEntity<String> InvalidCredentials(IOException ex) {

                return error(UNAUTHORIZED, ex);
        }

        @ResponseBody
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler({DataIntegrityViolationException.class})
        public ResponseEntity<String>  sqlConstraintViolationException(DataIntegrityViolationException ex) throws IOException {
                return error(BAD_REQUEST, ex);
        }

        private ResponseEntity<String> error(HttpStatus status, Exception e) {
                log.error("Exception : ", e);
                return ResponseEntity.status(status).body(e.getMessage());

        }
}
