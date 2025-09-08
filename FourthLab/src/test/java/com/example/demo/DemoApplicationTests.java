package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticated_should_get_401() throws Exception {
        mockMvc.perform(get("/api/owners/get"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void authenticated_user_can_get() throws Exception {
        mockMvc.perform(get("/api/owners/get"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void user_cannot_create_owner() throws Exception {
        String body = "{\"name\":\"John\",\"birthDate\":\"1990-01-01\"}";
        mockMvc.perform(post("/api/owners/create").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void admin_can_create_owner() throws Exception {
        String body = "{\"name\":\"Admin Owner\",\"birthDate\":\"1990-01-01\"}";
        mockMvc.perform(post("/api/owners/create").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isOk());
    }
}


