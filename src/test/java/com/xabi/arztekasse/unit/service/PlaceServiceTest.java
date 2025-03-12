package com.xabi.arztekasse.unit.service;

import com.xabi.arztekasse.controller.dto.PlaceDetailsDto;
import com.xabi.arztekasse.controller.dto.PlaceOverviewDto;
import com.xabi.arztekasse.data.BusinessStatus;
import com.xabi.arztekasse.data.OpeningHours;
import com.xabi.arztekasse.data.Place;
import com.xabi.arztekasse.exception.NotFoundException;
import com.xabi.arztekasse.repository.PlaceRepository;
import com.xabi.arztekasse.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        placeService = new PlaceService(placeRepository);
    }

    @Test
    void getAllPlaces_shouldReturnOverviewDtoList() {
        // Given
        List<Place> mockPlaces = List.of(
                Place.builder().id(1L).label("Test A").location("Test A").build(),
                Place.builder().id(2L).label("Test B").location("Test B").build()
        );
        when(placeRepository.findAll()).thenReturn(mockPlaces);

        // When
        List<PlaceOverviewDto> result = placeService.getAllPlaces();

        // Then: The result should match the mocked data
        assertThat(result).hasSize(2);
        PlaceOverviewDto item = result.getFirst();
        assertThat(item.id()).isEqualTo(1L);
        assertThat(item.label()).isEqualTo("Test A");
        assertThat(item.location()).isEqualTo("Test A");
    }

    @Test
    void getPlaceById_shouldReturnPlaceDetailsOnlyOpenHours() {
        // Given
        Place mockPlace = Place.builder()
                .id(1L)
                .label("Test A")
                .location("Address A")
                .openingHours(List.of(
                        OpeningHours.builder()
                                .dayOfWeek(1)
                                .startTime(LocalTime.now())
                                .endTime(LocalTime.now().plusHours(2)).build(),
                        OpeningHours.builder()
                                .dayOfWeek(2)
                                .startTime(LocalTime.now())
                                .endTime(LocalTime.now()
                                        .plusHours(2)).build()
                ))
                .build();

        when(placeRepository.findById(1L)).thenReturn(Optional.of(mockPlace));

        // When
        PlaceDetailsDto result = placeService.getPlaceById(1L, false);

        // Then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.label()).isEqualTo("Test A");
        assertThat(result.location()).isEqualTo("Address A");
        assertThat(result.businessHours().get(0).hours().getFirst().status()).isEqualTo(BusinessStatus.OPEN);
        assertThat(result.businessHours().get(1).hours().getFirst().status()).isEqualTo(BusinessStatus.OPEN);
        assertThat(result.businessHours().size()).isEqualTo(2);

    }
   @Test
    void getPlaceById_shouldReturnPlaceDetailsIncludeCloseDays() {
        // Given
        Place mockPlace = Place.builder()
                .id(1L)
                .label("Test A")
                .location("Address A")
                .openingHours(List.of(
                        OpeningHours.builder()
                                .dayOfWeek(1)
                                .startTime(LocalTime.now())
                                .endTime(LocalTime.now().plusHours(2)).build(),
                        OpeningHours.builder()
                                .dayOfWeek(2)
                                .startTime(LocalTime.now())
                                .endTime(LocalTime.now()
                                .plusHours(2)).build()
                ))
                .build();

        when(placeRepository.findById(1L)).thenReturn(Optional.of(mockPlace));

        // When
        PlaceDetailsDto result = placeService.getPlaceById(1L, true);

        // Then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.label()).isEqualTo("Test A");
        assertThat(result.location()).isEqualTo("Address A");
        assertThat(result.businessHours().get(0).hours().getFirst().status()).isEqualTo(BusinessStatus.OPEN);
        assertThat(result.businessHours().get(1).hours().getFirst().status()).isEqualTo(BusinessStatus.OPEN);
        assertThat(result.businessHours().size()).isEqualTo(7);
        assertThat(result.businessHours().get(2).hours().getFirst().status()).isEqualTo(BusinessStatus.CLOSED);
        assertThat(result.businessHours().get(3).hours().getFirst().status()).isEqualTo(BusinessStatus.CLOSED);
        assertThat(result.businessHours().get(4).hours().getFirst().status()).isEqualTo(BusinessStatus.CLOSED);
        assertThat(result.businessHours().get(5).hours().getFirst().status()).isEqualTo(BusinessStatus.CLOSED);
        assertThat(result.businessHours().get(6).hours().getFirst().status()).isEqualTo(BusinessStatus.CLOSED);

    }

    @Test
    void getPlaceById_shouldThrowNotFoundException_whenPlaceMissing() {
        // Given
        when(placeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.getPlaceById(999L, false))
                .isInstanceOf(NotFoundException.class);

    }
}