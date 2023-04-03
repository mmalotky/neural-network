package dev10.NeuralNetwork.models;

import java.util.HashMap;
import java.util.UUID;

public class Maze {
    private String mazeId;
    private final HashMap<String, MazeElement> maze = new HashMap<>();

    public Maze() {
        mazeId = UUID.randomUUID().toString();
    }

    public String getMazeId() {
        return mazeId;
    }

    public void setMazeId(String mazeId) {
        this.mazeId = mazeId;
    }

    public HashMap<String, MazeElement> getMaze() {
        return maze;
    }
}
