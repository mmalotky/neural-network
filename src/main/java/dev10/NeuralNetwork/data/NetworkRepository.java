package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;
import org.springframework.stereotype.Repository;

@Repository
public class NetworkRepository {
    public boolean saveNetwork(Network network) {
        return false;
    }

    public Network loadNetwork(String id) {
        return null;
    }
}
