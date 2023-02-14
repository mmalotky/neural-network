package dev10.NeuralNetwork.models;

import java.util.List;

public interface Node {
    void resetActivationState();

    List<Integer> getActivationState();

    void input(int state);
}
