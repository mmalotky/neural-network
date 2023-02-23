package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Option implements Node {

    private final int optionId;
    private final List<Double> activationState = new ArrayList<>();
    private double sum = 0;

    public Option(int optionId) {
        this.optionId = optionId;
    }

    @Override
    public void resetActivationState() {
        activationState.clear();
        sum = 0;
    }

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    @Override
    public void input(double state) {
        activationState.add(state);
        sum = activationState.stream().mapToDouble(Double::doubleValue).sum();
    }

    public int getOptionId() {
        return optionId;
    }

    public double getSum() {
        return sum;
    }
}
