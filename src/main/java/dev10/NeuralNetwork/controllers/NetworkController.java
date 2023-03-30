package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.data.NetworkData;
import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ann")
public class NetworkController {
    private Network network;

    @Autowired
    private final NetworkService service;

    public NetworkController(NetworkService service) {
        this.service = service;
    }

    @GetMapping("/saves")
    public ResponseEntity<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = service.getSavedNetworkIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<List<String>> saveNetwork() {
        Result<Void> result = service.saveNetwork(network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<List<String>> newNetwork(@RequestBody Map<String, int[]> map) {
        int options = map.get("options")[0];
        int[] layers = map.get("layers");
        Result<Network> result = service.newNetwork(options, layers);
        if(result.isSuccess()) {
            this.network = result.getPayload();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<List<String>> loadNetwork(@PathVariable String id) {
        Result<?> result = service.loadNetwork(id);
        if(result.isSuccess()) {
            this.network = (Network) result.getPayload();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rename")
    public ResponseEntity<List<String>> rename(@RequestBody String name) {
        if(network == null) {
            return new ResponseEntity<>(List.of("No network selected"), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.rename(name, network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/configuration")
    public ResponseEntity<List<String>> setLearningRate(@RequestBody double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            return new ResponseEntity<>(List.of("Out of Bounds"), HttpStatus.BAD_REQUEST);
        }
        network.setLearningRate(learningRate);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/configuration")
    public ResponseEntity<List<String>> getNetworkConfiguration() {
        if(network == null) {
            return new ResponseEntity<>(List.of("No network selected"), HttpStatus.BAD_REQUEST);
        }

        NetworkData data = new NetworkMapper().networkToData(network);
        return new ResponseEntity<>(data.getLines(), HttpStatus.OK);
    }
}
