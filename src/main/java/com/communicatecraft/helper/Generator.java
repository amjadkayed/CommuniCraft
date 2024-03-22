package com.communicatecraft.helper;

import org.springframework.validation.BindingResult;

import java.util.List;

public class Generator {
    private Generator() {
        throw new IllegalStateException("Generator class");
    }

    public static List<String>bindingResultToErrorList(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
    }
}
