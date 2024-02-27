package dev10.NeuralNetwork.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Result object for the service layer. Stores an object payload and error messages
 * @param <T> The object type for the payload
 */
public class Result<T> {
    private final List<String> errors = new ArrayList<>();
    private T payload;

    /**
     * Adds an error message to the result
     * @param error Message to be added
     */
    public void addError(String error) {
        errors.add(error);
    }

    /**
     * Get the errors list
     * @return A list of error messages
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Get the payload stored in the Result
     * @return The object in storage
     */
    public T getPayload() {
        return payload;
    }

    /**
     * Sets and object to be stored in the Result
     * @param payload The object to be stored
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * Checks the errors list to determine success or failure
     * @return Boolean value related to whether there are error messages
     */
    public boolean isSuccess() {
        return errors.isEmpty();
    }
}
