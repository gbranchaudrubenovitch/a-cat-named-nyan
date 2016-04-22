package com.gbr.nyan.feature;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanShowHomePage {
    @Autowired
    private TestRestTemplate httpClient;
    private ResponseEntity<String> rootResponse;

    @Before
    public void thisPage() {
        rootResponse = httpClient.getForEntity("/", String.class);
    }

    @Test
    public void returnsOkAndUtf8Html() throws Exception {
        String contentType = rootResponse.getHeaders().getContentType().toString();
        assertThat(rootResponse.getStatusCode(), is(OK));
        assertThat(contentType, is("text/html;charset=UTF-8"));
    }
}
