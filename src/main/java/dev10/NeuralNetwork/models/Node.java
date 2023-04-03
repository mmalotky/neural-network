package dev10.NeuralNetwork.models;

import java.util.List;

public interface Node {

    int getInputSize();

    void resetActivationState();

    List<Double> getActivationState();

    void input(int inputId, double state);

    double getErrorByState();

    void setErrorByState(double errorByState);
}
