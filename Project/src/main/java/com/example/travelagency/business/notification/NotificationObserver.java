package com.example.travelagency.business.notification;

import com.example.travelagency.model.Reservation;

public interface NotificationObserver {

    void update(Reservation reservation);
}
