package dev10.NeuralNetwork.controllers;

public enum Direction {
    DOWN(new int[]{1, 0}),
    UP(new int[]{-1, 0}),
    LEFT(new int[]{0, -1}),
    RIGHT(new int[]{0, 1});

    public final int[] value;
    Direction(int[] value) {
        this.value = value;
    }
}
