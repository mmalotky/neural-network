package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node in the final layer of the neural network, meant for option selection.
 */
public class Option implements Node {
    private final int optionId;

    private int inputSize;
    private int inputCount = 0;
    private final List<Double> activationState = new ArrayList<>();
    private double sum = 0;

    private double lastProbability = 0;

    private double errorByState;

    public Option(int optionId, int inputSize) {
        this.optionId = optionId;
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

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int inputId, double state) {
        activationState.set(inputId, state);
        inputCount++;
        if(inputCount == inputSize) {
            sum = activationState.stream().mapToDouble(Double::doubleValue).sum();
        }
    }

    /**
     * Gets the identifier for the Option
     * @return int identifier for th option
     */
    public int getOptionId() {
        return optionId;
    }

    @Override
    public double getSum() {
        return sum;
    }

    /**
     * Gets the calculated probability of the option given the last forward propagation
     * @return double probability
     */
    public double getLastProbability() {
        return lastProbability;
    }

    /**
     * Sets the calculated probability of the option given the last forward propagation
     * @param lastProbability double probability
     */
    public void setLastProbability(double lastProbability) {
        this.lastProbability = lastProbability;
    }

    @Override
    public double getErrorByState() {
        return errorByState;
    }

    @Override
    public void setErrorByState(double errorByState) {
        this.errorByState = errorByState;
    }
}
