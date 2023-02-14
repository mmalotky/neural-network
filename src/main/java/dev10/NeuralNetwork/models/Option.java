package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Option implements Node {
    private final List<Integer> activationState = new ArrayList<>();

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
        //TODO: add Markovian Decision
    }

}
