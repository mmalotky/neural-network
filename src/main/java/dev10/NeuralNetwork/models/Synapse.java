package dev10.NeuralNetwork.models;

public class Synapse {
    private final Neuron receiver;
    private int weight;

    public Synapse(Neuron receiver, int weight) {
        this.receiver = receiver;
        this.weight = weight;
    }

    public void transmit() {
        receiver.input(weight);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
