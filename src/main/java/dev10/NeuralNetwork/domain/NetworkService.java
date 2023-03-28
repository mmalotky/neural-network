package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.NetworkRepository;
import dev10.NeuralNetwork.models.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService {
    @Autowired
    NetworkRepository repository;

    public Result<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = new Result<>();
        try {
            List<String> networkIds = repository.getSavedNetworkIds();
            result.setPayload(networkIds);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }
    public Result<Void> saveNetwork(Network network) {
        Result<Void> result = new Result<>();
        try {
            repository.saveNetwork(network);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    public Result<Network> loadNetwork(String id) {
        Result<Network> result = new Result<>();
        try {
            Network network = repository.loadNetwork(id);
            result.setPayload(network);
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }
}
