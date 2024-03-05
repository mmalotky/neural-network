package dev10.NeuralNetwork.models;

import java.util.*;

/**
 * Artificial neural network composed of Nodes and Synapses. Facilitates forward and backpropagation
 */
public class Network {
    private String networkId;
    private final List<List<Neuron>> layers = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();

    private double learningRate = 0.05;

    private Option choice;

    private Option best;

    private double lastError;

    public Network(int options, int[] layers) {
        networkId = UUID.randomUUID().toString();

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

    /**
     * Gets the identifier for the network
     * @return String id
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * Sets the identifier for the network
     * @param networkId String id
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * Gets the hidden layers composed of Neurons
     * @return List of Lists of Neurons, each representing a separate layer
     */
    public List<List<Neuron>> getLayers() {
        return layers;
    }

    /**
     * Gets a list of the options for the output of the neural network
     * @return List of Option Nodes
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * Gets the option selected by Markovian decision-making
     * @return Option selected
     */
    public Option getChoice() {
        return choice;
    }

    /**
     * Gets the Option with the highest probability
     * @return Option selected
     */
    public Option getBest() {
        return best;
    }

    /**
     * Gets the current learning rate of the network
     * @return double value for learning modifier
     */
    public double getLearningRate() {
        return learningRate;
    }

    /**
     * Sets the learning rate of the network to modulate the rate of change due to backpropagation
     * @param learningRate double learning rate to be set
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * Propagates a set of inputs forward through the Network, then determines the result with softmax
     * @param states List of Double values for inputs
     * @throws NetworkConfigurationException
     */
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

    /**
     * Resets the activation states within the network to prepare the next forward propagation
     */
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

    /**
     * Backpropagation based calculated error between the output and the expected reward
     * @param reward double[] of expected rewards for each option
     */
    public void reverse(double[] reward) {
        lastError = 0;

        for (Option option : options) {
            double rValue = reward[option.getOptionId()];
            double errorByState = option.getSum() - rValue;

            option.setErrorByState(errorByState);
            lastError += 0.5 * Math.pow(errorByState, 2);
        }

        for (int i = layers.size() - 1; i >= 0; i--) {
            List<Neuron> layer = layers.get(i);
            for (Neuron neuron : layer) {
                double errorByState = 0;
                for (Synapse synapse : neuron.getConnections()) {
                    double active = neuron.getSum() > 0 ? 1 : 0.01;
                    errorByState += synapse.getReceiver().getErrorByState() * synapse.getWeight() * active;

                    double change = synapse.getReceiver().getErrorByState() * synapse.getLastOut();
                    synapse.setWeight(synapse.getWeight() - learningRate * change);
                }
                neuron.setErrorByState(errorByState);
            }
        }
    }

    /**
     * Implements Markovian decision-making by randomly selecting an option based on its probability
     */
    private void softMax() {
        double rand = Math.random();
        double totalValues = options.stream().mapToDouble(o -> Math.exp(o.getSum())).sum();
        double currentProbability = 0.0;
        boolean choosing = true;
        for(Option option : options) {
            double prob = Math.exp(option.getSum()) / totalValues;
            option.setLastProbability(prob);
            currentProbability += prob;
            if(rand <= currentProbability && choosing) {
                this.choice = option;
                choosing = false;
            }
        }

        best = options.stream()
                .max(Comparator.comparingDouble(Option::getLastProbability))
                .orElse(null);
    }

    /**
     * Gets the last calculated error
     * @return double value of error
     */
    public double getLastError() {
        return lastError;
    }
}
