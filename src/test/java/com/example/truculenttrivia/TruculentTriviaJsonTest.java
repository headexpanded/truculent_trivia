package com.example.truculenttrivia;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class TruculentTriviaJsonTest {

    @Autowired
    private JacksonTester<TriviaResponse> json;

    @Test
    public void triviaDataSerialisationTest() throws IOException {
        String expectedJson = new String(new ClassPathResource("expected.json").getInputStream().readAllBytes());

        TriviaData triviaData = new TriviaData("Geography",
                "multiple",
                "easy",
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                "Ohio", new String[] { "Florida",
                        "Idaho",
                        "New Mexico" });

        TriviaResponse triviaResponse = new TriviaResponse(0, new TriviaData[] { triviaData });

        assertThat(json.write(triviaResponse)).isEqualTo(expectedJson);
        assertThat(json.write(triviaResponse)).hasJsonPathNumberValue("@.response_code");
        assertThat(json.write(triviaResponse)).extractingJsonPathNumberValue("@.response_code").isEqualTo(0);
        assertThat(json.write(triviaResponse)).hasJsonPathArrayValue("@.results");
        assertThat(json.write(triviaResponse)).extractingJsonPathStringValue("@.results[0].category").isEqualTo("Geography");
        assertThat(json.write(triviaResponse)).extractingJsonPathStringValue("@.results[0].type").isEqualTo("multiple");
        assertThat(json.write(triviaResponse)).hasJsonPathStringValue("@.results[0].difficulty");
        assertThat(json.write(triviaResponse)).extractingJsonPathStringValue("@.results[0].difficulty").isEqualTo("easy");
        assertThat(json.write(triviaResponse)).hasJsonPathStringValue("@.results[0].question");
        assertThat(json.write(triviaResponse)).extractingJsonPathStringValue("@.results[0].question").isEqualTo(
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?");
        assertThat(json.write(triviaResponse)).hasJsonPathStringValue("@.results[0].correct_answer");
        assertThat(json.write(triviaResponse)).extractingJsonPathStringValue("@.results[0].correct_answer").isEqualTo("Ohio");
        assertThat(json.write(triviaResponse)).hasJsonPathArrayValue("@.results[0].incorrect_answers");
        assertThat(json.write(triviaResponse)).extractingJsonPathArrayValue("@.results[0].incorrect_answers").containsExactly(new Object[]{"Florida", "Idaho", "New Mexico"});
 
    }

    @Test
    public void triviaDataDeserializationTest() throws IOException {
        String expected = """
                {
                    "response_code": 0,
                    "results": [
                    {"category": "Geography",
                    "type": "multiple",
                    "difficulty": "easy",
                    "question": "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                    "correct_answer": "Ohio",
                    "incorrect_answers": ["Florida", "Idaho", "New Mexico"]}
                    ]
                }
                """;
        TriviaData expectedData = new TriviaData(
                "Geography",
                "multiple",
                "easy",
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                "Ohio",
                new String[] { "Florida", "Idaho", "New Mexico" });
        
        TriviaResponse expectedResponse = new TriviaResponse(0, new TriviaData[] { expectedData });

        assertThat(json.parseObject(expected).results()[0].category()).isEqualTo("Geography");
        assertThat(json.parseObject(expected).results()[0].type()).isEqualTo("multiple");
        assertThat(json.parseObject(expected).results()[0].difficulty()).isEqualTo("easy");
        assertThat(json.parseObject(expected).results()[0].question()).isEqualTo("What is the only state in the United States that does not have a flag in a shape with 4 edges?");
        assertThat(json.parseObject(expected).results()[0].correct_answer()).isEqualTo("Ohio");
        assertThat(json.parseObject(expected).results()[0].incorrect_answers()).containsExactly(new String[]{"Florida", "Idaho", "New Mexico"});

    }
}
