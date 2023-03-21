package dev10.NeuralNetwork.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Network {
    private final List<List<Neuron>> layers = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();

    private double learningRate = 0.5;

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

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void forward(List<Double> states) throws NetworkConfigurationException {
        if(states.size() != layers.get(0).size()) {
            throw new NetworkConfigurationException("Invalid Input", new Throwable().fillInStackTrace());
        }
        for(int i = 0; i < layers.get(0).size(); i++) {
            Neuron neuron = layers.get(0).get(i);
            Double state = states.get(i);
            neuron.input(0, state);
        }

        softMax();
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

    public void reverse(int expectedId) {
        for (Option option : options) {
            optionErrorByState(option, expectedId);
        }
        for (int i = layers.size() - 1; i >= 0; i--) {
            List<Neuron> layer = layers.get(i);
            for (Neuron neuron : layer) {
                double errorByState = 0;
                for (Synapse synapse : neuron.getConnections()) {
                    double change = synapse.getReceiver().getErrorByState() * synapse.getLastOut();
                    synapse.setWeight(synapse.getWeight() - learningRate * change);

                    double active = neuron.getSum() > 0 ? 1 : 0.01;
                    errorByState += synapse.getReceiver().getErrorByState() * synapse.getWeight() * active;
                }
                neuron.setErrorByState(errorByState);
            }
        }
    }

    private void optionErrorByState(Option option, int expectedId) {
        double errorByProb = option.getLastProbability() - (option.getOptionId() == expectedId ? 1 : 0);
        double outputSum = options.stream().mapToDouble(o -> Math.exp(o.getSum())).sum();
        double outputCurrent = Math.exp(option.getSum());
        double probByState = (outputSum - outputCurrent) * outputCurrent / Math.pow(outputSum, 2);
        option.setErrorByState(errorByProb * probByState);
    }

    private void softMax() {
        double rand = Math.random();
        double totalValues = options.stream().mapToDouble(o -> Math.exp(o.getSum())).sum();
        double currentProbability = 0.0;
        for(Option option : options) {
            double prob = Math.exp(option.getSum()) / totalValues;
            option.setLastProbability(prob);
            currentProbability += prob;
            if(rand <= currentProbability) {
                this.choice = option;
            }
        }
    }
}
