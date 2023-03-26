package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloAPIController.class)
public class HelloAPIControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void helloAPINoParameter() throws Exception {
        this.mvc.perform(get("/helloAPI"))
            .andExpect(status().isOk())
			.andExpect(content().string("Hello World!"));
    }
	
	@Test
	public void helloAPIWithName() throws Exception {
        this.mvc.perform(get("/helloAPI").param("name","John"))
            .andExpect(status().isOk())
			.andExpect(content().string("Hello John!"));
    }
	
	@Test
    public void EmailAPINoParameters() throws Exception {
        this.mvc.perform(get("/emailAPI"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.suggestedEmail").value("John.Doe@OntarioTechU.net"));
    }
	
	@Test
    public void EmailAPIWithFirstName() throws Exception {
        this.mvc.perform(get("/emailAPI").param("fname","Jack"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jack Doe"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.suggestedEmail").value("Jack.Doe@OntarioTechU.net"));
    }
	
	@Test
    public void EmailAPIWithLastName() throws Exception {
        this.mvc.perform(get("/emailAPI").param("lname","Sparrow"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Sparrow"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.suggestedEmail").value("John.Sparrow@OntarioTechU.net"));
    }
	
	@Test
    public void EmailAPIWithFullName() throws Exception {
        this.mvc.perform(get("/emailAPI").param("fname","Jack").param("lname","Sparrow"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jack Sparrow"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.suggestedEmail").value("Jack.Sparrow@OntarioTechU.net"));
    }
}