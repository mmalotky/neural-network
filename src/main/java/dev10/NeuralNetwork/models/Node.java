package dev10.NeuralNetwork.models;

import java.util.List;

public interface Node {
    void resetActivationState();

    List<Double> getActivationState();

    void input(double state);
}
