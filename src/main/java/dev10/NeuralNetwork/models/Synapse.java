package dev10.NeuralNetwork.models;

public class Synapse {
    private final Node receiver;
    private final int outputId;
    private double weight;

    public Synapse(Node receiver, int outputId, double weight) {
        this.receiver = receiver;
        this.outputId = outputId;
        this.weight = weight;
    }

    public void transmit(double value) {
        receiver.input(outputId, value * weight);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getOutputId() {
        return outputId;
    }
}
