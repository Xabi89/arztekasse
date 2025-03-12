package com.xabi.arztekasse.unit.controller;

import com.xabi.arztekasse.controller.PlaceController;
import com.xabi.arztekasse.controller.dto.PlaceDetailsDto;
import com.xabi.arztekasse.exception.NotFoundException;
import com.xabi.arztekasse.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PlaceController.class)
@ExtendWith(SpringExtension.class)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaceService placeService;

    @Test
    void getPlaceById_shouldReturnPlace_whenPlaceExists() throws Exception {
        // Given a valid PlaceDto
        PlaceDetailsDto mockPlace = new PlaceDetailsDto(1L, "Test", "Address", null);

        // When
        when(placeService.getPlaceById(1)).thenReturn(mockPlace);

        // Perform GET request
        mockMvc.perform(get("/api/v1/places/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPlaceById_shouldReturn404_whenPlaceNotFound() throws Exception {
        // Given
        when(placeService.getPlaceById(999))
                .thenThrow(new NotFoundException("Place with ID 999 not found"));

        // When
        mockMvc.perform(get("/api/v1/places/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                    {
                        "status": 404,
                        "message": "Place with ID 999 not found"
                    }
                """));
    }
}
