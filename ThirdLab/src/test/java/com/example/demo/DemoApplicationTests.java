package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Controllers.OwnerController;
import com.example.DTO.OwnerDTO;
import com.example.Services.OwnerService;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    private OwnerDTO testOwnerDto;
    private List<OwnerDTO> testOwners;

    @BeforeEach
    void setUp() {
        testOwnerDto = new OwnerDTO();
        testOwnerDto.setId(1L);
        testOwnerDto.setName("John Doe");
        testOwnerDto.setBirthDate(LocalDate.of(1990, 1, 1));
        
        testOwners = Arrays.asList(testOwnerDto);
    }

    @Test
    void getOwnerById_WhenExists_ShouldReturnOwner() throws Exception {
        when(ownerService.getOwnerById(1L)).thenReturn(Optional.of(testOwnerDto));

        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void deleteOwner_WhenNotExists_ShouldReturn404() throws Exception {
        testOwnerDto = new OwnerDTO();
        testOwnerDto.setId(1L);
        testOwnerDto.setName("John Doe");
        testOwnerDto.setBirthDate(LocalDate.of(1990, 1, 1));

        testOwners = Arrays.asList(testOwnerDto);
        when(ownerService.deleteOwner(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/owners/delete/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOwner_ShouldReturnCreatedOwner() throws Exception {
        OwnerDTO createdOwnerDto = new OwnerDTO();
        createdOwnerDto.setId(2L);
        createdOwnerDto.setName("Jane Doe");
        createdOwnerDto.setBirthDate(LocalDate.of(1995, 5, 5));

        when(ownerService.createOwner(any(OwnerDTO.class))).thenReturn(createdOwnerDto);

        mockMvc.perform(post("/api/owners/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.birthDate").value("1995-05-05"));
    }

    @Test
    void findByNamePaginated_ShouldReturnPaginatedResults() throws Exception {
        Page<OwnerDTO> page = new PageImpl<>(testOwners, PageRequest.of(0, 10), 1);
        when(ownerService.findByNamePaginated(anyString(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/owners/search/name/paginated")
                        .param("name", "John")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John Doe"));
    }
}
