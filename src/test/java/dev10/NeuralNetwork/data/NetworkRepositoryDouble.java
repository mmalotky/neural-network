package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;

import java.util.List;
import java.util.Objects;

public class NetworkRepositoryDouble implements NetworkRepository {

    private final Network test = new Network(2, new int[]{ 2, 2 });
    private final Network test2 = new Network(1, new int[]{1});

    public NetworkRepositoryDouble() {
        test.setNetworkId("test");
        test2.setNetworkId("test2");
    }

    @Override
    public List<String> getSavedNetworkIds() throws DataAccessException {
        return List.of("test", "test2");
    }

    @Override
    public void saveNetwork(Network network) throws DataAccessException {
    }

    @Override
    public Network loadNetwork(String id) throws DataAccessException {
        if(Objects.equals(id, test.getNetworkId())) {
            return test;
        } else if (Objects.equals(id, test2.getNetworkId())) {
            return test2;
        }
        else {
            throw new DataAccessException("Error accessing file at: {filePath}");
        }
    }

    @Override
    public boolean deleteNetwork(String id) {
        return true;
    }
}
