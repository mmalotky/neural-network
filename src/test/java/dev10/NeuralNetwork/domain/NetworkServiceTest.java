package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.NetworkRepositoryDouble;
import dev10.NeuralNetwork.models.Network;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkServiceTest {
    NetworkRepositoryDouble repository = new NetworkRepositoryDouble();
    NetworkService service = new NetworkService(repository);

    @Test
    void shouldGetNetworkIds() throws DataAccessException {
        Result<List<String>> result = service.getSavedNetworkIds();
        assertTrue(result.isSuccess());
        assertEquals(result.getPayload().get(0), "test.txt");
        assertEquals(result.getPayload().get(1), "test2.txt");
    }

    @Test
    void shouldSaveNetwork() {
        Network network = new Network(1, new int[]{1});
        Result<Void> result = service.saveNetwork(network);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotSaveNull() {
        Result<Void> result = service.saveNetwork(null);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidId() {
        Network network = new Network(1, new int[]{1});
        network.setNetworkId(null);
        Result<Void> result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId("");
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId("a/i");
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId(".no");
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId("#no");
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId("\n");
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidComponents() {
        Network network = new Network(0, new int[]{1});
        Result<Void> result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network = new Network(1, new int[]{0});
        result = service.saveNetwork(network);
        assertFalse(result.isSuccess());
    }
}