package com.example.travelagency.business.notification;

import com.example.travelagency.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class NotificationSubject {
    private List<NotificationObserver> observers;

    public NotificationSubject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(NotificationObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(NotificationObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(Reservation reservation) {
        for (NotificationObserver observer : observers) {
            observer.update(reservation);
        }
    }
}
