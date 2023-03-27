package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;

public interface NetworkRepository {
    void saveNetwork(Network network) throws DataAccessException;

    Network loadNetwork(String id) throws DataAccessException;
}
