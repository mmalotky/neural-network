package dev10.NeuralNetwork.models;

import java.util.List;

public class Neuron {
    private List<Synapse> connections;

    private List<Integer> activationState;

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

    public void resetActivationState() {
        activationState.clear();
    }

    public List<Integer> getActivationState() {
        return activationState;
    }

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
