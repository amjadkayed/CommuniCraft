package com.communicate_craft.utility;

import com.communicate_craft.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Validator {
    public static Map<String, String> convertBindingResultToMap(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    public static void validateBody(BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(convertBindingResultToMap(result));
        }
    }
}
