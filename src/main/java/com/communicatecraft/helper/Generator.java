package com.communicatecraft.helper;

import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Utility class for generating objects.
 * This class cannot be instantiated.
 */
public class Generator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * Throws an exception if called.
     */
    private Generator() {
        throw new IllegalStateException("Generator class");
    }

    /**
     * Converts a BindingResult object into a list of error messages.
     * Each error message is a string that contains the name of the field that caused the error and the default error message.
     * @param result the BindingResult object to convert.
     * @return a list of error messages.
     */
    public static List<String> bindingResultToErrorList(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
    }
}