package com.untold.untold.persistance;

import com.untold.untold.model.Admin;
import com.untold.untold.model.Band;
import com.untold.untold.model.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends JpaRepository<StaffUser, Long > {

    Admin findAdminByUsername(String username);
}
