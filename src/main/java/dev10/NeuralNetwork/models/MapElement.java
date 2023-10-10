package dev10.NeuralNetwork.models;

public enum MapElement {
    FLOOR(0.0),
    WALL(-1.0),
    START(0.0),
    END(1.0);

    public final double reward;

    MapElement(double reward) {
        this.reward = reward;
    }
}
