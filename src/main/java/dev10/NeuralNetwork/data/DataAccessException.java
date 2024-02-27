package dev10.NeuralNetwork.data;

/**
 * Exception to indicated errors in accessing data from the filesystem
 */
public class DataAccessException extends Exception {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

