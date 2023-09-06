package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.data.NetworkData;
import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import dev10.NeuralNetwork.models.NetworkConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class NetworkController {
    private Network network;

    @Autowired
    private final NetworkService service;

    public NetworkController(NetworkService service) {
        this.service = service;
    }

    public ResponseEntity<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = service.getSavedNetworkIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<String>> saveNetwork() {
        Result<Void> result = service.saveNetwork(network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<String>> newNetwork(Map<String, int[]> map) {
        int options = map.get("options")[0];
        int[] layers = map.get("layers");
        Result<Network> result = service.newNetwork(options, layers);
        if(result.isSuccess()) {
            this.network = result.getPayload();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<String>> loadNetwork(String id) {
        Result<?> result = service.loadNetwork(id);
        if(result.isSuccess()) {
            this.network = (Network) result.getPayload();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<String>> rename(String name) {
        if(network == null) {
            return new ResponseEntity<>(List.of("No network selected"), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.rename(name, network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<String>> setLearningRate(double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            return new ResponseEntity<>(List.of("Out of Bounds"), HttpStatus.BAD_REQUEST);
        }
        network.setLearningRate(learningRate);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<String>> getNetworkConfiguration() {
        if(network == null) {
            return new ResponseEntity<>(List.of("No network selected"), HttpStatus.BAD_REQUEST);
        }

        NetworkData data = new NetworkMapper().networkToData(network);
        return new ResponseEntity<>(data.getLines(), HttpStatus.OK);
    }

    public ResponseEntity<?> learn(List<List<Double>> testSet) throws NetworkConfigurationException {
        List<Double> inputs = testSet.get(0);
        double[] reward = testSet.get(1).stream().mapToDouble(Double::doubleValue).toArray();

        network.forward(inputs);
        network.reverse(reward);
        network.resetState();

        return new ResponseEntity<>(network.getChoice().getOptionId(), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> run(List<Double> input) throws NetworkConfigurationException {
        network.forward(input);
        return new ResponseEntity<>(network.getBest().getOptionId(), HttpStatus.OK);
    }

}
