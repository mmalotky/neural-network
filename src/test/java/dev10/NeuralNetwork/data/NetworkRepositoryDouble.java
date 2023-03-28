package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;

import java.util.List;

public class NetworkRepositoryDouble implements NetworkRepository {

    private final Network test2 = new Network(2, new int[]{ 2, 2 });

    @Override
    public List<String> getSavedNetworkIds() throws DataAccessException {
        return List.of("test.txt", "test2.txt");
    }

    @Override
    public void saveNetwork(Network network) throws DataAccessException {
    }

    @Override
    public Network loadNetwork(String id) throws DataAccessException {
        return test2;
    }
}
