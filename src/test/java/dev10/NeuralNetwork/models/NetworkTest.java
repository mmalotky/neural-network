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
        test1.setLearningRate(.5);

        assertEquals(2, test2.getLayers().size());
        assertEquals(2, test2.getLayers().get(0).size());
        assertEquals(2, test2.getOptions().size());
        test2.setLearningRate(.5);

        assertEquals(2, test3.getLayers().size());
        assertEquals(4, test3.getLayers().get(0).size());
        assertEquals(3, test3.getOptions().size());
        test3.setLearningRate(.5);
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
        assertTrue(0 < s1.getWeight());
        assertEquals(0, s1.getOutputId());

        Synapse s2 = test2.getLayers().get(1).get(1).getConnections().get(1);
        assertTrue(0 < s2.getWeight());
        assertEquals(1, s2.getOutputId());

        Synapse s3 = test3.getLayers().get(1).get(1).getConnections().get(2);
        assertTrue(0 < s3.getWeight());
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
        List<Double> input1 = new ArrayList<>();
        input1.add(1.0);
        test1.forward(input1);
        assertTrue(0 < test1.getOptions().get(0).getSum());
        test1.resetState();
        assertFalse(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));

        List<Double> input2 = new ArrayList<>();
        input2.add(.8);
        input2.add(.4);
        test2.forward(input2);
        assertTrue(0 < test2.getOptions().get(0).getSum());
        test2.resetState();
        assertFalse(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));

        List<Double> input3 = new ArrayList<>();
        input3.add(.8);
        input3.add(.2);
        input3.add(-.2);
        input3.add(.9);
        test3.forward(input3);
        assertTrue(0 < test3.getOptions().get(0).getSum());
        test3.resetState();
        assertFalse(test1.getChoice().getActivationState().stream().anyMatch(n -> n != 0.0));
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

    @Test
    void shouldBackPropagate() throws NetworkConfigurationException {
        List<Double> input2 = new ArrayList<>();
        input2.add(.9);
        input2.add(.4);

        double[] reward2 = {10, 0};

        for(int i = 0; i < 100; i++) {
            test2.forward(input2);
            test2.reverse(reward2);
            test2.resetState();
        }
        assertEquals(test2.getBest(), test2.getOptions().get(0));

        List<Double> input3 = new ArrayList<>();
        input3.add(.9);
        input3.add(0.1);
        input3.add(-.1);
        input3.add(.9);

        double[] reward3 = {-1, 7, 2};

        for(int i = 0; i < 100; i++) {
            test3.forward(input3);
            test3.reverse(reward3);
            test3.resetState();
        }
        assertEquals(test3.getBest(), test3.getOptions().get(1));
    }

    @Test
    void shouldBackPropagateMultipleInputs() throws NetworkConfigurationException {
        List<Double> input1 = new ArrayList<>();
        input1.add(0.1);
        input1.add(.1);
        input1.add(.9);
        input1.add(.5);

        double[] reward1 = {0, 5, 1};

        List<Double> input2 = new ArrayList<>();
        input2.add(.8);
        input2.add(.2);
        input2.add(.2);
        input2.add(.5);
        double[] reward2 = {3, -2, 0};

        for(int i = 0; i < 200; i++) {
            boolean flip = i%2 == 1;
            test3.forward((flip? input1:input2));
            test3.reverse((flip?reward1:reward2));
            test3.resetState();
        }

        test3.forward(input1);
        assertEquals(test3.getBest(), test3.getOptions().get(1));
        test3.resetState();

        test3.forward(input2);
        assertEquals(test3.getBest(), test3.getOptions().get(0));
        test3.resetState();
    }

    @Test
    void shouldFieldMultipleOutputsToSameInput() throws NetworkConfigurationException {
        List<Double> input1 = new ArrayList<>();
        input1.add(.9);
        input1.add(.8);
        input1.add(.7);
        input1.add(.6);

        double[] reward1 = {3, -1, -1};

        List<Double> input2 = new ArrayList<>();
        input2.add(.6);
        input2.add(.7);
        input2.add(.1);
        input2.add(.2);

        double[] reward2 = {1, 5, 0};

        List<Double> input3 = new ArrayList<>();
        input3.add(.3);
        input3.add(.4);
        input3.add(.9);
        input3.add(.8);

        double[] reward3 = {-1, 0, 4};

        List<Double> input4 = new ArrayList<>();
        input4.add(.9);
        input4.add(.1);
        input4.add(.8);
        input4.add(.2);

        double[] reward4 = {1, -3, -2};

        for (int i = 0; i < 400; i++) {
            test3.forward(input1);
            test3.reverse(reward1);
            test3.resetState();

            test3.forward(input2);
            test3.reverse(reward2);
            test3.resetState();

            test3.forward(input3);
            test3.reverse(reward3);
            test3.resetState();

            test3.forward(input4);
            test3.reverse(reward4);
            test3.resetState();
        }

        test3.forward(input1);
        test3.resetState();
        assertEquals(test3.getBest(), test3.getOptions().get(0));

        test3.forward(input2);
        test3.resetState();
        assertEquals(test3.getBest(), test3.getOptions().get(1));

        test3.forward(input3);
        test3.resetState();
        assertEquals(test3.getBest(), test3.getOptions().get(2));

        test3.forward(input4);
        test3.resetState();
        assertEquals(test3.getBest(), test3.getOptions().get(0));
    }
}