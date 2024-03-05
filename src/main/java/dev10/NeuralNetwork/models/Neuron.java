package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Component of a neural network that receives and sends signals to input and output synapses based on an activation
 * function processing the inputs.
 */
public class Neuron implements Node {
    private final List<Synapse> connections;
    private int inputSize;
    private int inputCount = 0;
    private final List<Double> activationState = new ArrayList<>();
    private double errorByState;
    private double sum;

    public Neuron(int inputSize, List<Synapse> connections) {
        this.connections = connections;
        setInputSize(inputSize);
    }

    @Override
    public int getInputSize() {
        return inputSize;
    }

    @Override
    public void resetActivationState() {
        Collections.fill(activationState, 0.0);
        inputCount = 0;
    }

    /**
     * Sets the number of inputs that must be filled before assessing activation. Should only be called during instantiation.
     * @param inputSize int number of inputs
     */
    private void setInputSize(int inputSize) {
        this.inputSize = inputSize;
        for(int i = 0; i < inputSize; i++) {
            activationState.add(0.0);
        }
    }

    /**
     * Gets a list of the Synapses this neuron outputs from
     * @return List of Synapse objects
     */
    public List<Synapse> getConnections() {
        return connections;
    }

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    /**
     * Adds a value to the activation state based on Leaky ReLu activation function.
     * @param inputId int id from the sender
     * @param state double input state
     */
    @Override
    public void input(int inputId, double state) {
        activationState.set(inputId, state);
        inputCount++;

        if(inputCount == inputSize) {
            this.sum = activationState.stream().mapToDouble(Double::doubleValue).sum();

            if(sum > 0) {
                output(sum);
            }
            else {
                output(0.01 * sum);
            }
        }
    }

    @Override
    public double getErrorByState() {
        return errorByState;
    }

    @Override
    public void setErrorByState(double errorByState) {
        this.errorByState = errorByState;
    }

    @Override
    public double getSum() {
        return sum;
    }

    /**
     * Transmit a value to the output synapses
     * @param value value to be transmitted
     */
    private void output(double value) {
        for(Synapse connection : connections) {
            connection.transmit(value);
        }
    }
}
