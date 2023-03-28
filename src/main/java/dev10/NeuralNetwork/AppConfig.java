package dev10.NeuralNetwork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public String getPathFormat() {
        return "./saves/networks/%s.txt";
    }
}
