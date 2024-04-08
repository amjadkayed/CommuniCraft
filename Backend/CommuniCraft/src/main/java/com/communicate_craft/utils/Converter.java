package com.communicate_craft.utils;

import org.springframework.validation.BindingResult;

import java.util.List;

public class Converter {
    private Converter() {
        throw new IllegalStateException("Converter class");
    }

    public static List<String> convertBindingResultToErrorList(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
    }
}
