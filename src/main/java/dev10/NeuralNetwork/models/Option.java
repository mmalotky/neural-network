package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Option implements Node {

    private final int optionId;
    private final List<Integer> activationState = new ArrayList<>();

    private double probability = 0.0;

    public Option(int optionId) {
        this.optionId = optionId;
    }

    @Override
    public void resetActivationState() {
        activationState.clear();
    }

    @Override
    public List<Integer> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int state) {
        activationState.add(state);
        //TODO: add probability function
        double probability = this.probability + 0.0;
        setProbability(probability);
    }

    private void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
