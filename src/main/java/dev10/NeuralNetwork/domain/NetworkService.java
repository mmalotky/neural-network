package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.NetworkFileRepository;
import dev10.NeuralNetwork.data.NetworkRepository;
import dev10.NeuralNetwork.models.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {
    @Autowired
    NetworkRepository repository;

    public Result<Void> saveNetwork(Network network) {
        boolean result = repository.saveNetwork(network);
        return null;
    }

    public Result<?> loadNetwork(String id) {
        Network network = repository.loadNetwork(id);
        return null;
    }
}
