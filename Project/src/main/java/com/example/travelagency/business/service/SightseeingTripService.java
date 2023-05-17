package com.example.travelagency.business.service;

import com.example.travelagency.repository.SightSeeingTripRepository;
import org.springframework.stereotype.Service;

@Service
public class SightseeingTripService {

    private final SightSeeingTripRepository sightSeeingTripRepository;

    public SightseeingTripService(SightSeeingTripRepository sightSeeingTripRepository) {
        this.sightSeeingTripRepository = sightSeeingTripRepository;
    }
}
