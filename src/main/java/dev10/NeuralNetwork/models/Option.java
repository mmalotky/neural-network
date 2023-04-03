package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

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

    private void setInputSize(int inputSize) {
        this.inputSize = inputSize;
        for(int i = 0; i < inputSize; i++) {
            activationState.add(0.0);
        }
    }

    @Override
    public void resetActivationState() {
        activationState.forEach(n -> n = 0.0);
        inputCount = 0;
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

    public int getOptionId() {
        return optionId;
    }

    public double getSum() {
        return sum;
    }

    public double getLastProbability() {
        return lastProbability;
    }

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
