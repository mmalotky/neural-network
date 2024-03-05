package dev10.NeuralNetwork.models;

import java.util.List;

/**
 * Component of a neural network
 */
public interface Node {

    /**
     * Gets the number of inputs node must receive before determining its activation state
     * @return int number of inputs
     */
    int getInputSize();

    /**
     * Resets the stored input values to zero to ready the next forward propagation
     */
    void resetActivationState();

    /**
     * Gets a list of inputs representing the current state of the node
     * @return List of double type inputs
     */
    List<Double> getActivationState();

    /**
     * Adds an input to the activation state of a Node
     * @param inputId int id from the sender
     * @param state double input state
     */
    void input(int inputId, double state);

    /**
     * Gets the calculated error based on the current activation state of the node for the purposes of
     * backpropagation.
     * @return double value representing error
     */
    double getErrorByState();

    /**
     * Sets the calculated error based on the state of the node
     * @param errorByState double value of error
     */
    void setErrorByState(double errorByState);

    /**
     * Gets the sum of the states stored in the activation state
     * @return double sum of all input states
     */
    double getSum();
}
