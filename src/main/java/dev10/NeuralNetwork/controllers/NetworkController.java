package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.data.FileData;
import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import dev10.NeuralNetwork.models.NetworkConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for ANN operations. Includes persistent data management as well as operations for the active network.
 */
@Controller
public class NetworkController {
    private Network network;

    @Autowired
    private final NetworkService service;

    public NetworkController(NetworkService service) {
        this.service = service;
    }

    /**
     * Gets a list of saved Network ids
     * @return Response Entity containing a list of ids on a success or error messages on a failure
     */
    public ResponseEntity<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = service.getSavedIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves the current active network
     * @return A Response Entity indicating success or failure
     */
    public ResponseEntity<List<String>> saveNetwork() {
        Result<Void> result = service.save(network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new Network, sets the network to active, and saves the network to persistent data
     * @param map A Map containing "options" and "layers" data used to create a new Network
     * @return A Response Entity indicating success or failure
     */
    public ResponseEntity<List<String>> newNetwork(Map<String, int[]> map) {
        int options = map.get("options")[0];
        int[] layers = map.get("layers");
        Result<Network> result = service.newNetwork(options, layers);
        if(result.isSuccess()) {
            this.network = result.getPayload();
            saveNetwork();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Loads a network from persistent data and activates it
     * @param id The String id of the network to be activated
     * @return A Response Entity indicating success or failure
     */
    public ResponseEntity<List<String>> loadNetwork(String id) {
        Result<?> result = service.load(id);
        if(result.isSuccess()) {
            this.network = (Network) result.getPayload();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Renames the active network without saving to persistent data
     * @param name The new Network id to be used
     * @return A Response entity indicating success or failure
     */
    public ResponseEntity<List<String>> setNetworkId(String name) {
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

    /**
     * Sets the learning rate of the active Network without saving
     * @param learningRate The new learning rate between 0 and 1
     * @return A Response Entity indicating success or failure
     */
    public ResponseEntity<List<String>> setLearningRate(double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            return new ResponseEntity<>(List.of("Out of Bounds"), HttpStatus.BAD_REQUEST);
        }
        network.setLearningRate(learningRate);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Gets the current configuration across the active Network
     * @return A response entity containing the configuration data of the Network or an error list on failure
     */
    public ResponseEntity<List<String>> getNetworkConfiguration() {
        if(network == null) {
            return new ResponseEntity<>(List.of("No network selected"), HttpStatus.BAD_REQUEST);
        }

        FileData data = new NetworkMapper().objectToData(network);
        return new ResponseEntity<>(data.getLines(), HttpStatus.OK);
    }

    /**
     * Forward propagates a set of inputs through the active Network, then back propagates giving a set of rewards
     * @param testRewards List of rewards corresponding to the selectable options
     * @param inputs The set of inputs to be propagated through the network
     * @return A Response Entity containing the Option id of the network's Markovian choice
     * @throws NetworkConfigurationException
     */
    public ResponseEntity<?> learn(List<Double> testRewards, List<Double> inputs) throws NetworkConfigurationException {
        double[] reward = testRewards.stream().mapToDouble(Double::doubleValue).toArray();
        network.forward(inputs);
        network.reverse(reward);
        network.resetState();

        return new ResponseEntity<>(network.getChoice().getOptionId(), HttpStatus.ACCEPTED);
    }

    /**
     * Forward propagates a set of inputs through the active Network without back propagating
     * @param input The set of inputs to be propagated through the Network
     * @return A Response Entity containing the Option id of the network's best choice
     * @throws NetworkConfigurationException
     */
    public ResponseEntity<?> run(List<Double> input) throws NetworkConfigurationException {
        network.forward(input);
        return new ResponseEntity<>(network.getBest().getOptionId(), HttpStatus.OK);
    }

    /**
     * Removes a Network from persistent data
     * @param name The id of the network to be removed
     * @return A Response Entity indicating success or failure
     */
    public ResponseEntity<List<String>> delete(String name) {
        Result<Void> result = service.delete(name);
        return result.isSuccess() ?
                new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK) :
                new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get the id of the active Network
     * @return String id of the active Network or null if no Network is active
     */
    public String getNetworkId() {
        return network != null ? network.getNetworkId() : null;
    }

    /**
     * Get the learning rate of the active Network
     * @return Learning rate of the active Network or null if no Network is active
     */
    public double getLearningRate() {
        return network != null ? network.getLearningRate() : 0;
    }

    /**
     * Get the error of the active Network
     * @return Error of the active Network or null if no Network is active
     */
    public double getError() {
        return network == null ? 0 : network.getLastError();
    }
}
