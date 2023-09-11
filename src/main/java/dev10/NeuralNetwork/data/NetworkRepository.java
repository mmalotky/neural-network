package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;

import java.util.List;

public interface NetworkRepository {
    List<String> getSavedNetworkIds() throws DataAccessException;

    void saveNetwork(Network network) throws DataAccessException;

    Network loadNetwork(String id) throws DataAccessException;

    boolean deleteNetwork(String id);
}
