package com.xabi.arztekasse.service;

import com.xabi.arztekasse.controller.dto.HoursDto;
import com.xabi.arztekasse.controller.dto.BusinessHoursDto;
import com.xabi.arztekasse.controller.dto.PlaceDetailsDto;
import com.xabi.arztekasse.controller.dto.PlaceOverviewDto;
import com.xabi.arztekasse.data.BusinessStatus;
import com.xabi.arztekasse.data.OpeningHours;
import com.xabi.arztekasse.data.Place;
import com.xabi.arztekasse.exception.NotFoundException;
import com.xabi.arztekasse.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceOverviewDto> getAllPlaces() {
       List<Place> placeList = placeRepository.findAll();
       return placeList.stream()
               .map(it-> new PlaceOverviewDto(it.getId(), it.getLabel(), it.getLocation()))
               .toList();
    }
    public PlaceDetailsDto getPlaceById(long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> {
                    log.error("Place with ID {} not found", placeId);
                    return new NotFoundException("Place ID: " + placeId + " not found");
                });

        List<BusinessHoursDto> businessHoursDto = groupOpeningHours(place.getOpeningHours());

        return new PlaceDetailsDto(
                place.getId(),
                place.getLabel(),
                place.getLocation(),
                businessHoursDto
        );
    }

    private List<BusinessHoursDto> groupOpeningHours(List<OpeningHours> openingHours) {
        Map<Integer, List<HoursDto>> groupedHours = new HashMap<>();

        for (OpeningHours bh : openingHours) {
            groupedHours
                    .computeIfAbsent(bh.getDayOfWeek(), k -> new ArrayList<>())
                    .add(new HoursDto(
                            bh.getStartTime(),
                            bh.getEndTime(),
                            BusinessStatus.OPEN
                    ));
        }

        for (int day = 1; day <= 7; day++) {
            // If we don't have an entry in our db, setup as close for that day
            if (!groupedHours.containsKey(day)) {
                groupedHours.put(day, List.of(
                        new HoursDto(null, null, BusinessStatus.CLOSED)
                ));
            }
        }

        return groupedHours.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> new BusinessHoursDto(entry.getKey(), entry.getValue()))
                    .toList();

    }
}
