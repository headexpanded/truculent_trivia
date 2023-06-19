package com.example.truculenttrivia;

public record TriviaResponse(
    int responseCode,
    TriviaData[] results
) {
    
}
