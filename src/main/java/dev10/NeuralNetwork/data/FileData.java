package dev10.NeuralNetwork.data;

import java.util.ArrayList;
import java.util.List;

public class FileData {
    private final List<String> lines = new ArrayList<>();

    public void addLine(String line) {
        lines.add(line);
    }

    public List<String> getLines() {
        return lines;
    }
}
