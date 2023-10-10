package dev10.NeuralNetwork;

import dev10.NeuralNetwork.controllers.Direction;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.models.NetworkConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AIConfig {
    private final MapController mapController;
    private final NetworkController networkController;

    public AIConfig(MapController mapController, NetworkController networkController) {
        this.mapController = mapController;
        this.networkController = networkController;
    }

    public void train() throws NetworkConfigurationException {
        List<Double> coordinates = Arrays.stream(mapController.getCoordinates())
                .mapToObj(c -> (double) c)
                .toList();
        List<Double> rewards = new ArrayList<>();
        for(Direction direction : Direction.values()) rewards.add(mapController.calculateReward(direction));

        ResponseEntity<?> result = networkController.run(coordinates);

        if(result.getStatusCode() != HttpStatus.OK) return;
        int optionId = (int) result.getBody();
        Direction direction = switch (optionId) {
            case 0 -> Direction.RIGHT;
            case 1 -> Direction.LEFT;
            case 2 -> Direction.UP;
            case 3 -> Direction.DOWN;
            default -> throw new NetworkConfigurationException("Incompatible Network", new Exception());
        };
        mapController.navigate(direction);

        networkController.learn(rewards);
    }
}
