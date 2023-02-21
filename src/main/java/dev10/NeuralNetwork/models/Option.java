package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Option implements Node {

    private final int optionId;
    private final List<Integer> activationState = new ArrayList<>();
    private int sum = 0;

    public Option(int optionId) {
        this.optionId = optionId;
    }

    @Override
    public void resetActivationState() {
        activationState.clear();
        sum = 0;
    }

    @Override
    public List<Integer> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int state) {
        activationState.add(state);
        sum = activationState.stream().mapToInt(Integer::intValue).sum();
    }

    public int getOptionId() {
        return optionId;
    }

    public int getSum() {
        return sum;
    }
}
