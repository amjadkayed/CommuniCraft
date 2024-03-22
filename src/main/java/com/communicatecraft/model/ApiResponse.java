package com.communicatecraft.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse <T> {
    private T data;
    private List<String> errorMessage;
    public ApiResponse(T data, List<String> errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}
