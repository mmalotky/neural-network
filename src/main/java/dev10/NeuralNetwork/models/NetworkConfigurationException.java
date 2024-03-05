package dev10.NeuralNetwork.models;

/**
 * Exception to indicate errors in a neural network's configuration, preventing it from functioning
 */
public class NetworkConfigurationException extends Exception {
    public NetworkConfigurationException (String message, Throwable rootCause) {
        super(message,rootCause);
    }
}
