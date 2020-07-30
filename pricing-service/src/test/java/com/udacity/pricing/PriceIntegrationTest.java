package com.udacity.pricing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPriceById() throws IOException {
        ResponseEntity<String> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1/", String.class);

        System.out.println(response.getBody());

        //Convert JSON to map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(response.getBody(), Map.class);


        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        //Get the value from map
        assertThat(map.get("price"), equalTo(14001.0));

    }



}
