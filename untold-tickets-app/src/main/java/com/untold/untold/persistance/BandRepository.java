package com.untold.untold.persistance;

import com.untold.untold.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository  extends JpaRepository<Band, Long > {

}
