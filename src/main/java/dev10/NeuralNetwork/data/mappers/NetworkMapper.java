package dev10.NeuralNetwork.data.mappers;

import dev10.NeuralNetwork.data.FileData;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.models.Neuron;
import dev10.NeuralNetwork.models.Synapse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts Network objects to data files and vice versa
 */
public class NetworkMapper implements Mapper<Network> {
    @Override
    public Network dataToObject(FileData data) {
        List<String> lines = data.getLines();
        int options = Integer.parseInt(lines.get(1).replaceAll("options=", ""));

        List<String> layersData = lines.stream().filter(line -> line.contains("layer=")).collect(Collectors.toList());
        int numberOfLayers = layersData.size();

        int[] layers = new int[numberOfLayers];
        List<String[]> processedLayers = new ArrayList<>();
        for(int i = 0; i < layersData.size(); i++) {
            String[] processedLayer = layersData.get(i).replaceAll("layer=\\[|]$", "").split("],\\[");
            processedLayers.add(i, processedLayer);
            layers[layers.length - 1 - i] = processedLayer.length;
        }

        Network network = new Network(options, layers);
        network.setNetworkId(lines.get(0).substring(3));
        for(int i = 0; i < network.getLayers().size(); i++) {
            List<Neuron> layer = network.getLayers().get(i);
            String[] processedLayer = processedLayers.get(i);

            for(int j = 0; j < layer.size(); j++) {
                Neuron neuron = layer.get(j);
                String[] connectionData = processedLayer[j].split(",");

                for (int k = 0; k < neuron.getConnections().size(); k++) {
                    Synapse synapse = neuron.getConnections().get(k);
                    double weight = Double.parseDouble(connectionData[k]);
                    synapse.setWeight(weight);
                }
            }
        }

        network.setLearningRate(Double.parseDouble(lines.get(lines.size() - 1).replace("learningRate=", "")));

        return network;
    }

    @Override
    public FileData objectToData(Network network) {
        String id = network.getNetworkId();
        String options = String.valueOf(network.getOptions().size());
        List<List<Neuron>> layers = network.getLayers();
        double learningRate = network.getLearningRate();

        FileData data = new FileData();
        data.addLine("id="+id);
        data.addLine("options="+options);

        for(List<Neuron> layer : layers) {
            StringBuilder layerData = new StringBuilder("layer=");
            for(int i = 0; i < layer.size(); i++) {

                List<Synapse> connections = layer.get(i).getConnections();
                StringBuilder connectionData = new StringBuilder(i==0 ? "[" : ",[");

                for(int j = 0; j < connections.size(); j++) {
                    String weight = String.valueOf(connections.get(j).getWeight());
                    connectionData.append(j == 0 ? weight : "," + weight);
                }
                connectionData.append("]");
                layerData.append(connectionData);
            }
            data.addLine(layerData.toString());
        }

        data.addLine("learningRate="+learningRate);

        return data;
    }
}
