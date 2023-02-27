package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Neuron implements Node {
    private final List<Synapse> connections;
    private int inputs;
    private final List<Double> activationState = new ArrayList<>();

    public Neuron(int inputs, List<Synapse> connections) {
        this.connections = connections;
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

    public void addConnection(Synapse connection) {
        connections.add(connection);
        setInputs(inputs + 1);
    }

    public List<Synapse> getConnections() {
        return connections;
    }

    @Override
    public void resetActivationState() {
        activationState.forEach(n -> n = 0.0);
    }

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int inputId, double state) {
        activationState.set(inputId, state);
        double inputSum = activationState.stream().mapToDouble(Double::doubleValue).sum();
        if(inputSum > 0) {
            output(inputSum);
        }
    }

    private void output(double value) {
        for(Synapse connection : connections) {
            connection.transmit(value);
        }
    }
}
