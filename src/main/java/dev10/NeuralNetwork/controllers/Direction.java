package dev10.NeuralNetwork.controllers;

public enum Direction {
    RIGHT(new int[]{1, 0}),
    LEFT(new int[]{-1, 0}),
    UP(new int[]{0, 1}),
    DOWN(new int[]{0, -1});

    public final int[] value;
    Direction(int[] value) {
        this.value = value;
    }
}
