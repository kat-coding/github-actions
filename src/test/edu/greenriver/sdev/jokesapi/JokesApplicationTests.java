package edu.greenriver.sdev.jokesapi;


import edu.greenriver.sdev.jokesapi.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
}
