package com.example.truculenttrivia;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TruculentTriviaApplicationTests {

	private static final String TRIVIA_URI = "https://opentdb.com";

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void shouldReturnHttpStatusOK() {
		ResponseEntity<String> response = restTemplate.getForEntity(TRIVIA_URI, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturnSingleGeographyEasyMultipleChoiceQuestion() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://opentdb.com/api.php?amount=1&category=22&difficulty=easy%type=multiple", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
