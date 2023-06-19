package com.example.truculenttrivia;


public record TriviaData(
    String category,
    String type,
    String difficulty,
    String question,
    String correctAnswer,
    String[] incorrectAnswers) {
}
