package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.models.Network;
import dev10.NeuralNetwork.domain.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/ann")
public class NetworkController {
    private Network network;

    @Autowired
    private NetworkService service;

    @PostMapping("/save")
    public HttpResponse<?> saveNetwork() {
        service.saveNetwork(network);
        return null;
    }

    @GetMapping("/load")
    public HttpResponse<?> loadNetwork(String id) {
        service.loadNetwork(id);
        return null;
    }
}
