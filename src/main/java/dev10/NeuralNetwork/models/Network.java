package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<List<Neuron>> layers = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();

    public Network(int options, int[] layers) {
        for(int i = 0; i < options; i++) {
            this.options.add(new Option(i));
        }
        for(int i = 0; i < layers.length; i++) {
            this.layers.add(new ArrayList<>());
            for (int j = 0; j < layers[i]; j++) {
                List<Synapse> connections = new ArrayList<>();
                for(Node n : i == 0 ? this.options : this.layers.get(i-1)) {
                    int weight = 1; //TODO: set up initial weight distribution
                    Synapse connection = new Synapse(n, weight);
                    connections.add(connection);
                }
                Neuron neuron = new Neuron(connections);
                this.layers.get(i).add(neuron);
            }
        }
    }

    public List<List<Neuron>> getLayers() {
        return layers;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void forward() {
        //TODO
    }
}
