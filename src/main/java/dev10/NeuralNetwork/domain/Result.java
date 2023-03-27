package dev10.NeuralNetwork.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private final List<String> errors = new ArrayList<>();
    private T payload;

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }
}
