package com.urlshortener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.urlshortener.controller.UrlController;
import com.urlshortener.dto.CreateUrlRequest;
import com.urlshortener.dto.CreateUrlResponse;
import com.urlshortener.service.UrlService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tools.jackson.databind.ObjectMapper;

@ExtendWith (MockitoExtension.class)
public class UrlControllerTest {
    
    @Mock 
    private UrlService urlservice;

    @InjectMocks 
    private UrlController urlcontroller;

    private MockMvc mockmvc;

    private com.fasterxml.jackson.databind.ObjectMapper objectmapper;

    @BeforeEach 
    void setup(){

        objectmapper = new com.fasterxml.jackson.databind.ObjectMapper().findAndRegisterModules();
                
        mockmvc = MockMvcBuilders.standaloneSetup(urlcontroller).build();        

            }

        
            @Test 
           void createUrl_ShouldReturn201Created() throws Exception {
            CreateUrlRequest request =  new CreateUrlRequest("https://www.google.com");

            CreateUrlResponse response = new CreateUrlResponse("AbCd1234",
                        "http://localhost:8080/AbCd1234",
                        "https://www.google.com",
                        Instant.now());


                        when(urlservice.createShortUrl(any(CreateUrlRequest.class)))
                        .thenReturn(response);

                       mockmvc.perform(
        post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(request))
)
.andExpect(status().isCreated())

                        .andExpect(jsonPath("$.shortCode").value("AbCd1234"))
                        .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/"))
                        .andExpect(jsonPath("$.originalUrl").value("https://www.google.com"))
                        .andExpect(jsonPath("$.createdAt").exists());

                        verify(urlservice,times(1)).createShortUrl(any(CreateUrlRequest.class));

           } 


           

}
