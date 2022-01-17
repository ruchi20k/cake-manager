package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.entity.Cake;
import com.example.demo.repository.CakeRepository;
import com.example.demo.service.CakeService;
import static org.hamcrest.core.StringContains.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CakeManagerApplicationTests {

    private static final String GET_CAKES = "/cakes";
    private static final String ADD_CAKES = "/saveCakes";
    
    public static final String VALID_USER_TEST_CREDENTIALS = "admin" + ":" + "password";

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    CakeService cakeService;

    @Test
    public void saveAllCakes() throws Exception {
	try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cakes.json")) {
	    String cake = new BufferedReader(
		    new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
		    .collect(Collectors.joining());

	    mockmvc.perform(MockMvcRequestBuilders.post(ADD_CAKES)
		    .header(HttpHeaders.AUTHORIZATION, getAuthToken(VALID_USER_TEST_CREDENTIALS))
		    .content(cake)
		    .contentType(MediaType.APPLICATION_JSON)
		    .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isCreated());
	}
    }

    @Test
    public void saveAndGetCakes() throws Exception{
	Cake cake = new Cake(1, 
		"Banana cake",
		"Donkey kongs favourite",
		"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg");

	cakeService.saveCake(cake);

	Cake cake1 = cakeService.getCakeById(1);

	assertEquals("Banana cake", cake1.getTitle());

	mockmvc.perform(MockMvcRequestBuilders.get(GET_CAKES)
		.header(HttpHeaders.AUTHORIZATION, getAuthToken(VALID_USER_TEST_CREDENTIALS))
		.accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk())
	.andExpect(content().string(containsString("title")))
	.andExpect(content().string(containsString("desc")))
	.andExpect(content().string(containsString("image")));
    }
    
    
    public static String getAuthToken(String credentials) {
        return "Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes());
    }

}
