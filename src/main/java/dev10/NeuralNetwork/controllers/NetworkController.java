package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ann")
public class NetworkController {
    private Network network;

    @Autowired
    private NetworkService service;

    @PostMapping("/save")
    public ResponseEntity<?> saveNetwork() {
        Result<Void> result = service.saveNetwork(network);
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<?> newNetwork(@RequestBody int options, int[] layers) {
        this.network = new Network(options, layers);
        return null;
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadNetwork(String id) {
        Result<?> result = service.loadNetwork(id);
        if(result.isSuccess()) {
            this.network = (Network) result.getPayload();
        }
        return null;
    }
}
