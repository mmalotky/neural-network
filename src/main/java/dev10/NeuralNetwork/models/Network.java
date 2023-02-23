package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<List<Neuron>> layers = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();

    private Option choice;

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

    public Option getChoice() {
        return choice;
    }

    public void forward(List<Integer> states) {
        if(states.size() != layers.get(0).size()) {
            System.out.println("Invalid Input");
            return; //TODO: throw exception?
        }
        for(int i = 0; i < layers.get(0).size(); i++) {
            Neuron neuron = layers.get(0).get(i);
            Integer state = states.get(i);
            neuron.input(state);
        }

        this.choice = softMax();

        for (List<Neuron> layer : layers) {
            for (Neuron neuron : layer) {
                neuron.resetActivationState();
            }
        }
    }

    public void reverse() {
        //TODO: back propagation
    }

    private Option softMax() {
        double rand = Math.random();
        double totalValues = options.stream().mapToDouble(Option::getSum).sum();
        double currentProbability = 0.0;
        for(Option option : options) {
            double probability = option.getSum() / totalValues;
            currentProbability += probability;
            if(rand <= currentProbability) {
                return option;
            }
        }

        return null;
    }
}
