package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ann")
public class NetworkController {
    private Network network;

    @Autowired
    private NetworkService service;

    @GetMapping("/saves")
    public ResponseEntity<List<String>> getSavedNetworkIds() {
        Result<List<String>> result = service.getSavedNetworkIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<List<String>> saveNetwork() {
        Result<Void> result = service.saveNetwork(network);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Void> newNetwork(@RequestBody int options, int[] layers) {
        this.network = new Network(options, layers);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/load")
    public ResponseEntity<List<String>> loadNetwork(String id) {
        Result<?> result = service.loadNetwork(id);
        if(result.isSuccess()) {
            this.network = (Network) result.getPayload();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
