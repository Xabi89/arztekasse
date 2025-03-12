package com.xabi.arztekasse.integration;

import com.xabi.arztekasse.controller.dto.PlaceDetailResponse;
import com.xabi.arztekasse.controller.dto.PlaceOverviewResponse;
import com.xabi.arztekasse.controller.dto.PlaceDetailsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PlaceControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPlaceById_shouldReturnPlace_whenPlaceExists() {

        // When
        ResponseEntity<PlaceDetailResponse> response =
                restTemplate.exchange(
                        "/api/v1/places/1",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PlaceDetailResponse>() {}
                );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlaceDetailResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();

        PlaceDetailsDto placeDetails = responseBody.data();
        assertThat(placeDetails).isNotNull();

        assertThat(placeDetails.id()).isEqualTo(1L);
        assertThat(placeDetails.label()).isEqualTo("Le Café du Marché");
        assertThat(placeDetails.location()).isEqualTo("Rue de Conthey 17, 1950 Sion");
    }

}

