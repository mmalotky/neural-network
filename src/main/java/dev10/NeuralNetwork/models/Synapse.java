package dev10.NeuralNetwork.models;

/**
 * The connection that links the nodes of the network and transmits signals between them based on internal weighing
 */
public class Synapse {
    private final Node receiver;
    private final int outputId;
    private double weight;

    private double lastOut;

    public Synapse(Node receiver, int outputId, double weight) {
        this.receiver = receiver;
        this.outputId = outputId;
        this.weight = weight;
    }

    /**
     * Outputs a value modified by the weight
     * @param value double value of the signal before weighting
     */
    public void transmit(double value) {
        this.lastOut = value;
        receiver.input(outputId, value * weight);
    }

    /**
     * Sets the weight of the synapse
     * @param weight double value of the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the current weight modifier for the synapse
     * @return double value of the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the id to be transmitted to the Node along with the output value
     * @return int id to be sent
     */
    public int getOutputId() {
        return outputId;
    }

    /**
     * Gets the value of the last output signal
     * @return double value of the output
     */
    public double getLastOut() {
        return lastOut;
    }

    /**
     * Gets the Node the receives signals from the synapse
     * @return Node that receives signals
     */
    public Node getReceiver() {
        return receiver;
    }
}
