package com.example.travelagency.business.xml;

import com.example.travelagency.model.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clients")
class ClientList {
    private List<Client> clients;

    public ClientList() {}

    public ClientList(List<Client> clients) {
        this.clients = clients;
    }

    @XmlElement(name = "client")
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}





