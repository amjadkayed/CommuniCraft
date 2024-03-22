package com.communicatecraft.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse <T> {
    private T data;
    private List<String> errorMessage;
}
