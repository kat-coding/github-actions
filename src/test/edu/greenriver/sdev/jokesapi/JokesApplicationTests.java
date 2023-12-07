package edu.greenriver.sdev.jokesapi;


import edu.greenriver.sdev.jokesapi.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JokesApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;
    @Test
    public void contextLoads(){

    }
    @Test
    public void getAllJokes(){
        String endpoint = "http://localhost:" + port+ "/jokes";
        //assemble request
        HttpEntity request = new HttpEntity(new HttpHeaders());
        //
        ResponseEntity<Joke[]> response = template.exchange(endpoint, HttpMethod.GET, request, Joke[].class);
        int status = response.getStatusCode().value();
        Joke[] jokes = response.getBody();

        assertEquals(status,200);
        assertTrue(jokes.length > 0);

    }
    @Test
    public void createJokeTest(){
        String endpoint = "http://localhost:" + port + "/jokes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Joke joke = new Joke("Knock, knock! Who's there? Doctor... Doctor who?");
        HttpEntity request = new HttpEntity(joke, headers);
        ResponseEntity<Joke> response = template.exchange(endpoint, HttpMethod.POST, request, Joke.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertTrue(response.hasBody());
        assertTrue(response.getBody().getId() > 0);
    }

    @Test
    public void updateTest(){
        String endpoint = "http://localhost:" + port + "/jokes";
        HttpHeaders headers = new HttpHeaders();

        Joke joke = new Joke("new text");
        joke.setId(1);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(joke, headers);
        ResponseEntity<Joke> response = template.exchange(endpoint, HttpMethod.PUT, request, Joke.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getJokeText(), joke.getJokeText());
    }
    @Test
    public void deleteTest(){
        String endpoint = "http://localhost:" + port + "/jokes";
        HttpHeaders headers = new HttpHeaders();

        Joke joke = new Joke("new text");
        joke.setId(1);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(joke, headers);
        ResponseEntity<Joke> response = template.exchange(endpoint, HttpMethod.DELETE, request, Joke.class);

        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}
