package dev10.NeuralNetwork.models;

public class Synapse {
    private final Node receiver;
    private double weight;

    public Synapse(Node receiver, double weight) {
        this.receiver = receiver;
        this.weight = weight;
    }

    public void transmit(double value) {
        receiver.input(value * weight);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
