package com.untold.untold.persistance;

import com.untold.untold.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository  extends JpaRepository<Concert,Long> {


    @Query("SELECT c FROM Concert c JOIN c.bands b WHERE b.id = :bandId")
    List<Concert> findConcertsByBandId(@Param("bandId") Long bandId);
}
