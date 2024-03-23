package com.communicatecraft.exceptions;

/**
 * Custom exception class for handling duplicated field errors.
 * This class extends the RuntimeException class, meaning it is an unchecked exception.
 * Unchecked exceptions are exceptions that can be thrown during the execution of the program but can be prevented by proper coding.
 */
public class DuplicatedFieldException extends RuntimeException{

    /**
     * Constructor for the DuplicatedFieldException class.
     * This constructor calls the constructor of the superclass (RuntimeException) and passes a message to it.
     * @param message the detail message, saved for later retrieval by the Throwable.getMessage() method.
     */
    public DuplicatedFieldException(String message) {
        super(message);
    }
}