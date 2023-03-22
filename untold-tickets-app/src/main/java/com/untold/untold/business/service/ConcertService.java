package com.untold.untold.business.service;
import com.untold.untold.model.Concert;
import com.untold.untold.persistance.BandRepository;
import com.untold.untold.persistance.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepo;
    private final BandRepository bandRepo;

    @Autowired
    public ConcertService(ConcertRepository concertRepo, BandRepository bandRepo) {
        this.concertRepo = concertRepo;
        this.bandRepo = bandRepo;
    }

    public Concert createConcert(Concert concert) {
        /*List<Band> bands = concert.getBands();
        List<Band> newBands = new ArrayList<>();
        for( Band b : bands) {
            Band band1 = (Band) bandRepo.findById(b.getId()).orElse(null);
            newBands.add(band1);
        }
        concert.setBands(newBands);
        */
        return concertRepo.save(concert);
    }

    public Concert getConcertById(long id) {
        return (Concert) concertRepo.findById(id).orElse(null);
    }

    public List<Concert> getAllConcerts() {
        return concertRepo.findAll();
    }

    public Concert updateConcert(Concert concert) {
       Concert existingConcert = concertRepo.findById(concert.getId()).orElse(null);
        if (existingConcert != null) {
            existingConcert.setName(concert.getName());
            existingConcert.setDescription(concert.getDescription());
            existingConcert.setDateAndTime(concert.getDateAndTime());
            existingConcert.setMaxTickets(concert.getMaxTickets());
            //
//
//            List<Band> newBands = new ArrayList<>();
//            for( Band b: concert.getBands()){
//                Band b2 = bandRepo.getReferenceById(b.getId());
//                newBands.add(b2);
//            }
//            existingConcert.setBands(newBands);
            existingConcert.setBands(concert.getBands());
            return concertRepo.save(existingConcert);
        }
        return null;
    }

    public void deleteConcert(long id) {
        concertRepo.deleteById(id);
    }

    public List<Concert> findConcertByBand(long id){
       return concertRepo.findConcertsByBandId(id);
    }
}
