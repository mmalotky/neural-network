package dev10.NeuralNetwork;

import dev10.NeuralNetwork.controllers.Direction;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.models.MapElement;
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
                .mapToObj(c -> (double) c + 1)
                .toList();

        List<Double> rewards = new ArrayList<>();
        for(Direction direction : Direction.values()) rewards.add(calculateReward(direction));

        ResponseEntity<?> result = networkController.learn(rewards, coordinates);

        if(result.getStatusCode() != HttpStatus.ACCEPTED) return;
        int optionId = (int) result.getBody();
        Direction direction = switch (optionId) {
            case 0 -> Direction.DOWN;
            case 1 -> Direction.UP;
            case 2 -> Direction.LEFT;
            case 3 -> Direction.RIGHT;
            default -> throw new NetworkConfigurationException("Incompatible Network", new Exception());
        };
        mapController.navigate(direction);
    }

    public double calculateReward(Direction direction) {
        int[] coordinates = mapController.getCoordinates();
        int[] location = {coordinates[0] + direction.value[0], coordinates[1] + direction.value[1]};
        MapElement el =  mapController.getCoordinatesElement(location);
        double reward = el == null ? -1.0 : el.reward;

        int[] end = mapController.getEnd();
        int xDiff = Math.abs(end[0] - coordinates[0]) - Math.abs(end[0] - location[0]);
        int yDiff = Math.abs(end[1] - coordinates[1]) - Math.abs(end[1] - location[1]);
        reward += (xDiff + yDiff)/10f;

        return reward;
    }
}
