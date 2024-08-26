package com.example.ecommerce_backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate;

    public RecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getRecommendations(List<String> product_history, List<Float> rating_history, String user_id) {
        // Build the URL with query parameters
        System.out.println(user_id);
        for(String x: product_history) System.out.println(x);
        for(Float x: rating_history) System.out.println(x);
        URI uri = UriComponentsBuilder
                .fromUriString("http://fastapi:8000/recommend_lists/")
                .queryParam("product_history", product_history)
                .queryParam("rating_history", rating_history)
                .queryParam("user_id", user_id)
                .build()
                .toUri();

        // Make the GET request
        System.out.println(uri);
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(uri, String[].class);
        System.out.println("Call done");
        List<String> result = Arrays.asList(responseEntity.getBody());
        for(String x: result) System.out.println(x);
        return result;
    }
}