package com.communicatecraft.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Represents a generic API response.
 * This class is parameterized with a type parameter T.
 * The T type parameter represents the type of data that this API response will contain.
 * This class also contains a list of error messages that can be used to send error information to the client.
 */
@Data
@AllArgsConstructor
public class ApiResponse <T> {

    /**
     * The data of the API response.
     * The type of this data is determined by the T type parameter.
     */
    private T data;

    /**
     * A list of error messages.
     * This can be used to send error information to the client.
     */
    private List<String> errorMessage;
}