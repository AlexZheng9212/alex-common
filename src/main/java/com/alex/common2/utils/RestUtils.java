package com.alex.common2.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.vavr.control.Either;

public class RestUtils {
  public static <L, R> ResponseEntity<Object> eitherBadResponse(Either<L, R> response) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    if (response.isRight()) {
      return new ResponseEntity<>(response.get(), responseHeaders, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(response.getLeft(), responseHeaders, HttpStatus.BAD_REQUEST);
    }
  }
}
