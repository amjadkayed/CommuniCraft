package com.communicate_craft.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ErrorsResponse {
    private List<String> errors;

    public ErrorsResponse(String... error) {
        errors = new ArrayList<>();
        Collections.addAll(errors, Arrays.copyOf(error, error.length));
    }

    public void addError(String error) {
        errors.add(error);
    }
}
