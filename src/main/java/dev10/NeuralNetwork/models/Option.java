package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Option implements Node {
    private final int optionId;

    private int inputs;
    private final List<Double> activationState = new ArrayList<>();
    private double sum = 0;

    private double lastProbability = 0;

    public Option(int optionId, int inputs) {
        this.optionId = optionId;
        setInputs(inputs);
    }

    @Override
    public int getInputs() {
        return inputs;
    }

    private void setInputs(int inputs) {
        this.inputs = inputs;
        for(int i = 0; i < inputs; i++) {
            activationState.add(0.0);
        }
    }

    @Override
    public void resetActivationState() {
        activationState.forEach(n -> n = 0.0);
        sum = 0;
    }

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int inputId, double state) {
        activationState.set(inputId, state);
        sum = activationState.stream().mapToDouble(Double::doubleValue).sum();
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
}
