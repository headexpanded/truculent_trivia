package com.example.truculenttrivia;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class TruculentTriviaJsonTest {

    @Autowired
    private JacksonTester<TriviaData> json;

    @Test
    public void triviaDataSerialisationTest() throws IOException {
        String expectedJson = new String(new ClassPathResource("expected.json").getInputStream().readAllBytes());

        TriviaData triviaData = new TriviaData("Geography",
                "Multiple Choice",
                "Easy",
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                "Ohio", new String[] { "Florida",
                        "Idaho",
                        "New Mexico" });

        assertThat(json.write(triviaData)).isEqualToJson(expectedJson);
        assertThat(json.write(triviaData)).hasJsonPathStringValue("@.category");
        assertThat(json.write(triviaData)).extractingJsonPathStringValue("@.category").isEqualTo("Geography");
        assertThat(json.write(triviaData)).hasJsonPathStringValue("@.type");
        assertThat(json.write(triviaData)).extractingJsonPathStringValue("@.type").isEqualTo("Multiple Choice");
        assertThat(json.write(triviaData)).hasJsonPathStringValue("@.difficulty");
        assertThat(json.write(triviaData)).extractingJsonPathStringValue("@.difficulty").isEqualTo("Easy");
        assertThat(json.write(triviaData)).hasJsonPathStringValue("@.question");
        assertThat(json.write(triviaData)).extractingJsonPathStringValue("@.question").isEqualTo(
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?");
        assertThat(json.write(triviaData)).hasJsonPathStringValue("@.correctAnswer");
        assertThat(json.write(triviaData)).extractingJsonPathStringValue("@.correctAnswer").isEqualTo("Ohio");
        assertThat(json.write(triviaData)).hasJsonPathArrayValue("@.incorrectAnswers");
        assertThat(json.write(triviaData)).extractingJsonPathArrayValue("@.incorrectAnswers").containsExactly(new Object[]{"Florida", "Idaho", "New Mexico"});

    }

    @Test
    public void triviaDataDeserializationTest() throws IOException {
        String expected = """
                {
                    "category": "Geography",
                    "type": "Multiple Choice",
                    "difficulty": "Easy",
                    "question": "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                    "correctAnswer": "Ohio",
                    "incorrectAnswers": ["Florida", "Idaho", "New Mexico"]
                }
                """;
        TriviaData expectedData = new TriviaData(
                "Geography",
                "Multiple Choice",
                "Easy",
                "What is the only state in the United States that does not have a flag in a shape with 4 edges?",
                "Ohio",
                new String[] { "Florida", "Idaho", "New Mexico" });

        assertThat(json.parseObject(expected).correctAnswer()).isEqualTo(expectedData.correctAnswer());
        //assertThat(Arrays.equals(json.parseObject(expected).incorrectAnswers(), expectedData.incorrectAnswers()));

    }
}
