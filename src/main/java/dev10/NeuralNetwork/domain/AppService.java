package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.FileRepository;

import java.util.List;

public abstract class AppService<T> {
    final FileRepository<T> repository;
    public AppService(FileRepository<T> repository) {
        this.repository = repository;
    }
    public Result<List<String>> getSavedIds() {
        Result<List<String>> result = new Result<>();
        try {
            List<String> ids = repository.getSavedIds();
            result.setPayload(ids);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    abstract Result<Void> save(T object);

    public Result<T> load(String id) {
        Result<T> result = new Result<>();
        checkId(id, result);
        if(!result.isSuccess()) {
            return result;
        }

        try {
            T object = repository.load(id);
            result.setPayload(object);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    public Result<Void> delete(String id) {
        Result<Void> result = new Result<>();
        try {
            if(!repository.getSavedIds().contains(id)) result.addError("File Not Found");
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        if(!repository.delete(id)) result.addError("Failed to Delete");
        return result;
    }

    void checkId(String id, Result<?> result) {
        if(id == null) {
            result.addError("Id cannot be null");
            return;
        }

        if(!id.matches("[a-zA-Z0-9-]+")) {
            result.addError("Id is empty or contains illegal characters");
        }
    }
}
