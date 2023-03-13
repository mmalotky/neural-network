package dev10.NeuralNetwork.models;

import java.util.List;

public interface Node {

    int getInputs();

    void resetActivationState();

    List<Double> getActivationState();

    void input(int inputId, double state);

    double getError();

    void setError(double error);
}
