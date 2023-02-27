package dev10.NeuralNetwork.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {
    private final Network test1 = new Network(1, new int[]{ 1 });
    private final Network test2 = new Network(2, new int[]{ 2, 2 });
    private final Network test3 = new Network(3, new int[]{ 5, 4 });

    @Test
    void shouldInstantiateNetwork() {
        assertEquals(1, test1.getLayers().size());
        assertEquals(1, test1.getLayers().get(0).size());
        assertEquals(1, test1.getOptions().size());

        assertEquals(2, test2.getLayers().size());
        assertEquals(2, test2.getLayers().get(0).size());
        assertEquals(2, test2.getOptions().size());

        assertEquals(2, test3.getLayers().size());
        assertEquals(4, test3.getLayers().get(0).size());
        assertEquals(3, test3.getOptions().size());
    }

    @Test
    void shouldInstantiateNeurons() {
        Neuron n1 = test1.getLayers().get(0).get(0);
        assertEquals(1, n1.getActivationState().size());
        assertEquals(1, n1.getConnections().size());

        Neuron n2 = test2.getLayers().get(1).get(1);
        assertEquals(2, n2.getActivationState().size());
        assertEquals(2, n2.getConnections().size());

        Neuron n3 = test3.getLayers().get(1).get(1);
        assertEquals(4, n3.getActivationState().size());
        assertEquals(3, n3.getConnections().size());
    }

    @Test
    void shouldInstantiateSynapses() {
        Synapse s1 = test1.getLayers().get(0).get(0).getConnections().get(0);
        assertEquals(1, s1.getWeight());
        assertEquals(0, s1.getOutputId());

        Synapse s2 = test2.getLayers().get(1).get(1).getConnections().get(1);
        assertEquals(1, s2.getWeight());
        assertEquals(1, s2.getOutputId());

        Synapse s3 = test3.getLayers().get(1).get(1).getConnections().get(2);
        assertEquals(1, s3.getWeight());
        assertEquals(1, s3.getOutputId());
    }

    @Test
    void shouldInstantiateOptions() {
        Option o1 = test1.getOptions().get(0);
        assertEquals(0, o1.getOptionId());
        assertEquals(1, o1.getActivationState().size());

        Option o2 = test2.getOptions().get(1);
        assertEquals(1, o2.getOptionId());
        assertEquals(2, o2.getActivationState().size());

        Option o3 = test3.getOptions().get(2);
        assertEquals(2, o3.getOptionId());
        assertEquals(5, o3.getActivationState().size());
    }

    @Test
    void shouldPropagateForward() throws NetworkConfigurationException {
        List<Integer> input1 = new ArrayList<>();
        input1.add(1);
        test1.forward(input1);
        assertEquals(1, test1.getOptions().get(0).getSum());
        test1.resetState();
        assertTrue(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));

        List<Integer> input2 = new ArrayList<>();
        input2.add(1);
        input2.add(0);
        test2.forward(input2);
        assertEquals(2, test2.getOptions().get(0).getSum());
        test2.resetState();
        assertTrue(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));

        List<Integer> input3 = new ArrayList<>();
        input3.add(1);
        input3.add(0);
        input3.add(-1);
        input3.add(2);
        test3.forward(input3);
        assertEquals(15, test3.getOptions().get(0).getSum());
        test3.resetState();
        assertTrue(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));
    }

    @Test
    void shouldNotForwardInvalidInput() {
        try {
            test1.forward(new ArrayList<>());
            fail();
        } catch (NetworkConfigurationException e) {
            assertEquals("Invalid Input", e.getMessage());
        }
    }
}