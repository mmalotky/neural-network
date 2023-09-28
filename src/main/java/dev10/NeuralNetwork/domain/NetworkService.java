package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.FileRepository;
import dev10.NeuralNetwork.models.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService {
    @Autowired
    FileRepository<Network> repository;

    public NetworkService(FileRepository<Network> repository) {
        this.repository = repository;
    }

    public Result<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = new Result<>();
        try {
            List<String> networkIds = repository.getSavedIds();
            result.setPayload(networkIds);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }
    public Result<Void> saveNetwork(Network network) {
        Result<Void> result = new Result<>();

        if(network == null) {
            result.addError("Network cannot be null");
            return result;
        }

        checkNetworkId(network.getNetworkId(), result);
        int[] layers = new int[network.getLayers().size()];
        for(int i = 0; i < network.getLayers().size(); i++) {
            layers[i] = network.getLayers().get(i).size();
        }

        checkNetworkComponents(network.getOptions().size(), layers, result);
        if(!result.isSuccess()) {
            return result;
        }

        try {
            repository.save(network, network.getNetworkId());
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    public Result<Network> newNetwork(int options, int[] layers) {
        Result<Network> result = new Result<>();

        checkNetworkComponents(options, layers, result);
        if(!result.isSuccess()) {
            return result;
        }

        Network network = new Network(options, layers);
        result.setPayload(network);
        return result;
    }

    public Result<Network> loadNetwork(String id) {
        Result<Network> result = new Result<>();
        checkNetworkId(id, result);
        if(!result.isSuccess()) {
            return result;
        }

        try {
            Network network = repository.load(id);
            result.setPayload(network);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    private void checkNetworkId(String id, Result<?> result) {
        if(id == null) {
            result.addError("Network Id cannot be null");
            return;
        }

        if(!id.matches("[a-zA-Z0-9-]+")) {
            result.addError("Network Id is empty or contains illegal characters");
        }
    }

    private void checkNetworkComponents(int options, int[] layers, Result<?> result) {
        if(options < 1) {
            result.addError("There must be at least one option.");
        }
        if(layers == null || layers.length == 0) {
            result.addError("There must be at least one layer");
        }
        else {
            for(int layer : layers) {
                if(layer < 1) {
                    result.addError("There must be at least one neuron in a layer");
                    break;
                }
            }
        }
    }

    public Result<Void> rename(String name, Network network) {
        Result<Void> result = new Result<>();
        checkNetworkId(name, result);
        network.setNetworkId(name);
        return result;
    }

    public Result<Void> delete(String name) {
        Result<Void> result = new Result<>();
        try {
            if(!repository.getSavedIds().contains(name)) result.addError("File Not Found");
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        if(!repository.delete(name)) result.addError("Failed to Delete");
        return result;
    }
}
