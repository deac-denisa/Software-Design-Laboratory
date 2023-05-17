package com.example.travelagency.business.xml;

import com.example.travelagency.model.Vacation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vacations")
class VacationList {
    private List<Vacation> clients;

    public VacationList() {}

    public VacationList(List<Vacation> clients) {
        this.clients = clients;
    }

    @XmlElement(name = "vacation")
    public List<Vacation> getVacations() {
        return clients;
    }

    public void setVacations(List<Vacation> clients) {
        this.clients = clients;
    }
}





