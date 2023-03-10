package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Network {
    private final List<List<Neuron>> layers = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();

    private Option choice;

    public Network(int options, int[] layers) {
        for(int i = 0; i < options; i++) {
            this.options.add(new Option(i, layers[0]));
        }
        for(int i = 0; i < layers.length; i++) {
            this.layers.add(new ArrayList<>());

            for (int j = 0; j < layers[i]; j++) {
                List<Synapse> connections = new ArrayList<>();

                for(Node n : i == 0 ? this.options : this.layers.get(i-1)) {
                    double weight = Math.random();
                    Synapse connection = new Synapse(n, j, weight);
                    connections.add(connection);
                }
                int inputs = i < layers.length - 1 ? layers[i + 1] : 1;
                Neuron neuron = new Neuron(inputs, connections);

                this.layers.get(i).add(neuron);
            }
        }
        Collections.reverse(this.layers);
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

    public void forward(List<Integer> states) throws NetworkConfigurationException {
        if(states.size() != layers.get(0).size()) {
            throw new NetworkConfigurationException("Invalid Input", new Throwable().fillInStackTrace());
        }
        for(int i = 0; i < layers.get(0).size(); i++) {
            Neuron neuron = layers.get(0).get(i);
            Integer state = states.get(i);
            neuron.input(0, state);
        }

        this.choice = softMax();
    }

    public void resetState() {
        for (List<Neuron> layer : layers) {
            for (Neuron neuron : layer) {
                neuron.resetActivationState();
            }
        }

        for (Option option : options) {
            option.resetActivationState();
        }
    }

    public void reverse(List<Double> probabilities) {
        double cost = 0.0;
        for(int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            double expected = probabilities.get(i);
            cost += 0.5 * Math.pow((option.getLastProbability() - expected), 2);
        }
        //TODO: back propagation
    }

    private Option softMax() {
        double rand = Math.random();
        double min = options.stream().mapToDouble(Option::getSum).min().orElse(0);
        double mod = min > 0 ? 0.0 : min * -1.0 + 1.0;
        double totalValues = options.stream().mapToDouble(o -> o.getSum() + mod).sum();
        double currentProbability = 0.0;
        for(Option option : options) {
            option.setLastProbability(option.getSum() / totalValues);
            currentProbability += option.getLastProbability();
            if(rand <= currentProbability) {
                return option;
            }
        }

        return null;
    }
}
