package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.FileRepository;

import java.util.List;

/**
 * Service Layer for persistent data
 * @param <T> Object to be stored
 */

public abstract class AppService<T> {
    final FileRepository<T> repository;
    public AppService(FileRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Get a list of object IDs from persistent data
     * @return Result object containing errors or the list of ids
     */
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

    /**
     * Save an object to persistent data
     * @param object The object to be saved
     * @return Result object indicating success or failure
     */
    abstract Result<Void> save(T object);

    /**
     * Load an object from persistent data
     * @param id The id of the object to be loaded
     * @return Result object indicating success or failure
     */
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

    /**
     * Remove an object from persistent data
     * @param id The id of the object to be removed
     * @return Result object indicating success or failure
     */
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

    /**
     * Validate id input
     * @param id Input to be validated
     * @param result Result object for error collection
     */
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
