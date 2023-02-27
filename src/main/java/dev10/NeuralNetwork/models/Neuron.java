package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Neuron implements Node {
    private final List<Synapse> connections;
    private final List<Double> activationState = new ArrayList<>();

    public Neuron(List<Synapse> connections) {
        this.connections = connections;
    }

    public void addConnection(Synapse connection) {
        connections.add(connection);
    }

    public void removeConnection(Synapse connection) {
        connections.remove(connection);
    }

    public List<Synapse> getConnections() {
        return connections;
    }

    @Override
    public void resetActivationState() {
        activationState.clear();
    }

    @Override
    public List<Double> getActivationState() {
        return activationState;
    }

    @Override
    public void input(double state) {
        activationState.add(state);
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
