package com.example.truculenttrivia;

public record TriviaResponse(
    int response_code,
    TriviaData[] results
) {
    
}
