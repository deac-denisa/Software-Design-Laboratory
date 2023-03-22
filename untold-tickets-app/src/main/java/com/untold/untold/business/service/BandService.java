package com.untold.untold.business.service;

import com.untold.untold.model.Band;
import com.untold.untold.model.Cashier;
import com.untold.untold.persistance.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    private final BandRepository bandRepository;

    @Autowired
    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    //create
    public Band createBand(Band band) {
        return bandRepository.save(band);
    }

    //retrieve
    public Band getBandById(Long id) {
        return (Band) bandRepository.findById(id).orElse(null);
    }

    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    //update
    public Band updateBand(Band band) {
        Band existingBand = (Band) bandRepository.findById(band.getId()).orElse(null);
        if (existingBand != null) {

            existingBand.setName(band.getName());
            existingBand.setGenre(band.getGenre());
            return bandRepository.save(existingBand);
        } else {
            return null;
        }
    }

    //delete
    public void deleteBandById(Long id) {
        bandRepository.deleteById(id);
    }
}
