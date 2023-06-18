package com.example.truculenttrivia;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class TruculentTriviaJsonTest {

    @Autowired
    private JacksonTester<TriviaQuestion> json;

    @Test
    public void triviaDataSerialisationTest() throws IOException {
        TriviaQuestion triviaQuestion = new TriviaQuestion("Who was the first drummer in The Beatles?", "Bob Dylan",
                "Mick Jagger", "Pete Best", "Ringo Starr");

        assertThat(json.write(triviaQuestion)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(triviaQuestion)).hasJsonPathStringValue("@.question");
        assertThat(json.write(triviaQuestion)).extractingJsonPathStringValue("@.answer1").isEqualTo("Bob Dylan");
        assertThat(json.write(triviaQuestion)).hasJsonPathStringValue("@.answer2");
        assertThat(json.write(triviaQuestion)).extractingJsonPathStringValue("@.answer2").isEqualTo("Mick Jagger");
        assertThat(json.write(triviaQuestion)).hasJsonPathStringValue("@.answer3");
        assertThat(json.write(triviaQuestion)).extractingJsonPathStringValue("@.answer3").isEqualTo("Pete Best");
        assertThat(json.write(triviaQuestion)).hasJsonPathStringValue("@.answer4");
        assertThat(json.write(triviaQuestion)).extractingJsonPathStringValue("@.answer4").isEqualTo("Ringo Starr");

    }
    
    @Test
    public void triviaDataDeserialisationTest() throws IOException {
        String expected = """
                {
  "question": "Who won WW2?",
  "answer1": "The Germans",
  "answer2": "The Martians",
  "answer3": "The Commies",
  "answer4": "The Kiwis"
}
                                """;
        assertThat(json.parse(expected)).isEqualTo(
                new TriviaQuestion("Who won WW2?", "The Germans", "The Martians", "The Commies", "The Kiwis"));
        assertThat(json.parseObject(expected).answer1()).isEqualTo("The Germans");
    }
    
}
