package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Neuron implements Node {
    private final List<Synapse> connections;

    private final List<Integer> activationState = new ArrayList<>();

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
    public List<Integer> getActivationState() {
        return activationState;
    }

    @Override
    public void input(int state) {
        activationState.add(state);
        boolean activate = checkActivation();
        if(activate) {
            output();
        }
    }

    private void output() {
        for(Synapse connection : connections) {
            connection.transmit();
        }
    }

    private boolean checkActivation() {
        //TODO: activation algorithm
        return false;
    }
}
