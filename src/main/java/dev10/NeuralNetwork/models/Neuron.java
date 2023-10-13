package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Neuron implements Node {
    private final List<Synapse> connections;
    private int inputSize;
    private int inputCount = 0;
    private final List<Double> activationState = new ArrayList<>();
    private double errorByState;
    private double sum;

    public Neuron(int inputSize, List<Synapse> connections) {
        this.connections = connections;
        setInputSize(inputSize);
    }

    @Override
    public int getInputSize() {
        return inputSize;
    }

    @Override
    public void resetActivationState() {
        Collections.fill(activationState, 0.0);
        inputCount = 0;
    }

    private void setInputSize(int inputSize) {
        this.inputSize = inputSize;
        for(int i = 0; i < inputSize; i++) {
            activationState.add(0.0);
        }
    }

    public List<Synapse> getConnections() {
        return connections;
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
            this.sum = activationState.stream().mapToDouble(Double::doubleValue).sum();

            if(sum > 0) {
                output(sum);
            }
            else {
                output(0.01 * sum);
            }
        }
    }

    @Override
    public double getErrorByState() {
        return errorByState;
    }

    @Override
    public void setErrorByState(double errorByState) {
        this.errorByState = errorByState;
    }

    public double getSum() {
        return sum;
    }

    private void output(double value) {
        for(Synapse connection : connections) {
            connection.transmit(value);
        }
    }
}
