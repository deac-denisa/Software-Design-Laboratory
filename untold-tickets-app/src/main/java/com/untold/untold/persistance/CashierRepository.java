package com.untold.untold.persistance;

import com.untold.untold.model.Admin;
import com.untold.untold.model.Cashier;
import com.untold.untold.model.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierRepository extends JpaRepository<StaffUser, Long > {

    Cashier findCashierByUsername(String username);

}
