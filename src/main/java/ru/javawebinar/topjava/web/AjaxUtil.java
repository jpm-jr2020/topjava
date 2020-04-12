package ru.javawebinar.topjava.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.StringJoiner;

public class AjaxUtil {

    private AjaxUtil() {
    }

    public static Optional<ResponseEntity<String>> checkBinding(BindingResult result) {
        if (result.hasErrors()) {
            StringJoiner joiner = new StringJoiner("<br>");
            result.getFieldErrors().forEach(
                    fe -> joiner.add(String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
            );
            return Optional.of(ResponseEntity.unprocessableEntity().body(joiner.toString()));
        } else {
            return Optional.empty();
        }
    }

}