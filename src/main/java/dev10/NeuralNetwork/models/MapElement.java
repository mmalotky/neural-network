package dev10.NeuralNetwork.models;

/**
 * Elements that represent components of a Navigable map. Each is assigned a value representing its contribution
 * to the total calculated rewards.
 */
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
